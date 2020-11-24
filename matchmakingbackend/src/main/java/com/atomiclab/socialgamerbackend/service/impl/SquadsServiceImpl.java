package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.RequestSquad;
import com.atomiclab.socialgamerbackend.domain.model.Chat;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Squad;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.ChatService;
import com.atomiclab.socialgamerbackend.service.FriendService;
import com.atomiclab.socialgamerbackend.service.SquadsService;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Es el servicio que maneja la lógica de los grupos privados (squads), que además tienen chat grupal y videollamadas
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class SquadsServiceImpl implements SquadsService {

    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    @Autowired
    FirebaseStorage firebaseStorage;
    @Autowired
    FriendService friendService;
    @Autowired
    ChatService chatService;
    /**
     * crear el objeto en la base de datos
     * @param squad Objeto squad
     * @param token String con el token del usuario
     * @return Squad un objeto Squad con sus respectivos atributos
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public Squad createSquad(Squad squad, String token) throws InterruptedException, ExecutionException {
        String chatId = chatService.createChatSquads(token);
        List<Person> persons = new ArrayList<>();
        String id = "";
        if (chatId != null) {
            squad.setChat_id(chatId);
            id = firebaseCrud.createVoidAndGetId("Squad");
            squad.setId_squad(id);
            squad.setAdmin(firebaseSecAuth.getEmail(token));
            boolean funciono = firebaseCrud.update(id, "Squad", squad);
            if (funciono) {
                CollectionReference collectionIntegrantes = firebaseCrud.getCollection("Squad").document(id)
                        .collection("Integrantes");
                Person personAux = firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token))
                        .toObject(Person.class);
                personAux.setPersona_id(firebaseSecAuth.getEmail(token));
                if (personAux != null) {
                    collectionIntegrantes.add(personAux);
                    persons.add(personAux);
                    squad.setIntegrantes(persons);
                }
            }

        }
        return squad;
    }
    /**
     * actualizar los campos en la base de datos.
     * @param squad Objeto squad
     * @return boolean que confirma si se pudo actualizar o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public boolean updateSquad(Squad squad) throws InterruptedException, ExecutionException {
        if (squad.getIntegrantes().size() <= 8) {
            return firebaseCrud.update(squad.getId_squad(), "Squad", squad);
        }
        return false;
    }
    /**
     * Elimina un squad de la base de datos
     * @param squadId Identificador del Squad
     * @return boolean que confirma si se pudo eliminar o no 
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public boolean deleteSquad(String squadId) throws InterruptedException, ExecutionException {
        Squad squad = firebaseCrud.getById("Squad", squadId).toObject(Squad.class);
        if (squad != null) {
            firebaseCrud.delete(squad.getChat_id(), "Chat");
        }
        return firebaseCrud.delete(squadId, "Squad");
    }
    /**
     * Retorna un squad de la base de datos
     * @param squadId Identificador del Squad
     * @return boolean que confirma si se pudo eliminar o no 
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public Squad getSquad(String squadId) throws InterruptedException, ExecutionException {
        Squad squad = firebaseCrud.getById("Squad", squadId).toObject(Squad.class);
        List<Person> integrantesToJoinSquad = new ArrayList<>();
        List<QueryDocumentSnapshot> integrantes = firebaseCrud.getSubCollection("Squad", squadId, "Integrantes").get()
                .get().getDocuments();
        for (DocumentSnapshot doc : integrantes) {
            if (doc != null) {
                Person person = doc.toObject(Person.class);
                integrantesToJoinSquad.add(person);
            }
        }
        squad.setIntegrantes(integrantesToJoinSquad);
        return squad;
    }
    /**
     * creará una invitación a cada usuario para unirse.
     * @param integrantes Lista de los integrantes que quiere invitar
     * @param token String con el token del usuario
     * @param nombreSquad nombre del Squad
     * @param idSquad Identificador del squad
     * @return boolean que confirma si se pudo eliminar o no 
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean sendInvitations(List<String> integrantes, String token, String nombreSquad, String idSquad)
            throws InterruptedException, ExecutionException {
        int contVal = 0;
        Person personAuxMyself = firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token))
                .toObject(Person.class);
        for (String integrante : integrantes) {
            RequestSquad request = new RequestSquad();
            if (integrante != null) {
                Person personAux = firebaseCrud.getById("Persona", integrante).toObject(Person.class);
                request.setRemitente(personAuxMyself);
                request.setReceptor(personAux);
                request.setNombreSquad(nombreSquad);
                request.setIdSquad(idSquad);
                boolean valido = firebaseCrud.save("InvitacionesSquads", request);
                if (valido == true) {
                    contVal = contVal + 1;
                }
            }
        }
        if (contVal == integrantes.size()) {
            return true;
        }
        return false;
    }
    /**
     * Retorna la lista de integrantes de un Squad
     * @param squadId Identificador de un squad
     * @return Lista de inegrantes de un squad
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Person> getIntegrantes(String squadId) throws InterruptedException, ExecutionException {
        DocumentSnapshot doc = firebaseCrud.getById("Squad", squadId);
        List<Person> integrantes = new ArrayList<>();
        if (doc != null) {
            Squad squad = doc.toObject(Squad.class);
            for (Person integrante : squad.getIntegrantes()) {
                if (integrante != null) {
                    Person person = firebaseCrud.getById("Persona", integrante.getPersona_id()).toObject(Person.class);
                    if (person != null) {
                        integrantes.add(person);
                    }
                }
            }
        }
        return integrantes;
    }
    /**
     * Retorna la lista de squads creados por amigos a partir del token de autenticación.
     * @param token String con el token del usuario
     * @return lista de aquads creados por amigos
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public Set<Squad> getFriendsSquads(String token) throws InterruptedException, ExecutionException {
        List<String> friendsId = new ArrayList<>();
        CollectionReference collection = firebaseCrud.getCollection("Squad");
        Set<Squad> friendsSquads = new HashSet<>();
        for (Person friend : friendService.getFriends(token, firebaseSecAuth.getEmail(token))) {
            if (friend != null) {
                friendsSquads.addAll(getFriendSquads(friend.getPersona_id(), firebaseSecAuth.getEmail(token)));
            }
        }
        return friendsSquads;
    }
    /**
     * Retorna la lista de squads con la que esta un amigo y el usuario
     * @param friendId Identificador del amigo
     * @param myEmail Email del usuario que hace la petición
     * @return Lista de squads donde pertenece el usuario y el amigo
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public List<Squad> getFriendSquads(String friendId, String myEmail)
            throws InterruptedException, ExecutionException {
        CollectionReference collection = firebaseCrud.getCollection("Squad");
        List<Squad> friendsSquads = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        boolean heInTheSquad = false, meInTheSquad = false;
        for (DocumentSnapshot doc : collection.get().get().getDocuments()) {
            if (doc != null) {
                Squad squad = (doc.toObject(Squad.class));
                if (squad.isVisibilidad()) {
                    heInTheSquad = false;
                    persons = new ArrayList<>();
                    List<QueryDocumentSnapshot> integrantesDocs = firebaseCrud
                            .getSubCollection("Squad", doc.getId(), "Integrantes").get().get().getDocuments();
                    meInTheSquad = imInTheSquad(integrantesDocs, myEmail);
                    for (DocumentSnapshot docIntegrantes : integrantesDocs) {
                        if (docIntegrantes != null) {
                            Person person = docIntegrantes.toObject(Person.class);
                            persons.add(person);
                            if (meInTheSquad == false && person.getPersona_id().equals(friendId)) {
                                heInTheSquad = true;
                            }
                        }
                    }
                    if (heInTheSquad == true) {
                        squad.setIntegrantes(persons);
                        friendsSquads.add(squad);
                    }
                }

            }
        }
        return friendsSquads;
    }
    /**
     * Retorna la lista de squads propios
     * @param token String con el token del usuario
     * @return Lista de squads del usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Squad> getMySquads(String token) throws InterruptedException, ExecutionException {
        CollectionReference collection = firebaseCrud.getCollection("Squad");
        List<Squad> mySquads = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        boolean imInTheSquad = false;
        for (DocumentSnapshot doc : firebaseCrud
                .collectionGroupSearch("Integrantes", "persona_id", firebaseSecAuth.getEmail(token)).get().get()
                .getDocuments()) {
            imInTheSquad = false;
            persons = new ArrayList<>();
            if (doc != null) {
                Squad squadImIn = doc.getReference().getParent().getParent().get().get().toObject(Squad.class);
                System.out.println(squadImIn.getId_squad());
                for (DocumentSnapshot docIntegrantes : firebaseCrud
                        .getSubCollection("Squad", squadImIn.getId_squad(), "Integrantes").get().get().getDocuments()) {
                    if (docIntegrantes != null) {
                        Person person = docIntegrantes.toObject(Person.class);
                        persons.add(person);
                        if (person.getPersona_id().equals(firebaseSecAuth.getEmail(token))) {
                            imInTheSquad = true;
                        }
                    }
                }
                if (imInTheSquad == true) {
                    squadImIn.setIntegrantes(persons);
                    mySquads.add(squadImIn);
                }
            }
        }
        return mySquads;
    }
    /**
     * Saber si el usuario pertenece o no a un Squad
     * @param integrantesDocs Integrantes de un squad
     * @param myEmail email del usuario para verificar si esta o no
     * @return boolean que confirma si es o no miembro de un squad
     */
    public boolean imInTheSquad(List<QueryDocumentSnapshot> integrantesDocs, String myEmail) {
        for (DocumentSnapshot doc : integrantesDocs) {
            if (doc != null) {
                Person person = doc.toObject(Person.class);
                if (person.getPersona_id().equals(myEmail)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Saca al usuario del squad
     * @param token String con el token del usuario
     * @param squad Objeto squad
     * @return boolean que confirma si lo eliminaron o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean exitSquad(String token, Squad squad) throws InterruptedException, ExecutionException {
        List<QueryDocumentSnapshot> query = firebaseCrud.getSubCollection("Squad", squad.getId_squad(), "Integrantes")
                .get().get().getDocuments();
        boolean delete = false, updateChat = false;
        if (query.size() > 1) {
            for (DocumentSnapshot doc : query) {
                if (doc != null) {
                    Person person = doc.toObject(Person.class);
                    if (person.getPersona_id().equals(firebaseSecAuth.getEmail(token))) {
                        delete = firebaseCrud.deleteInSubCollection(squad.getId_squad(), "Squad", doc.getId(),
                                "Integrantes");
                    } else {
                        if (firebaseSecAuth.getEmail(token).equals(squad.getAdmin())) {
                            squad.setAdmin(person.getPersona_id());
                            firebaseCrud.update(squad.getId_squad(), "Squad", squad);
                        }
                    }
                }
            }
            Chat chat = firebaseCrud.getById("Chat", squad.getChat_id()).toObject(Chat.class);
            List<String> newIntegrantes = new ArrayList<>();
            if (chat != null) {
                for (String integrante : chat.getIntegrantes()) {
                    if (integrante != null) {
                        if (!integrante.equals(firebaseSecAuth.getEmail(token))) {
                            newIntegrantes.add(integrante);
                        }
                    }
                }
                chat.setIntegrantes(newIntegrantes);
                updateChat = firebaseCrud.update(squad.getChat_id(), "Chat", chat);
            }
        } else {
            String idMemberToDelete = firebaseCrud.getSubCollection("Squad", squad.getId_squad(), "Integrantes").get()
                    .get().getDocuments().get(0).getId();
            firebaseCrud.deleteInSubCollection(squad.getId_squad(), "Squad", idMemberToDelete, "Integrantes");
            delete = deleteSquad(squad.getId_squad());
            updateChat = firebaseCrud.delete(squad.getChat_id(), "Chat");
        }

        return delete && updateChat;
    }
    /**
     * lista de invitaciones para unirse a un squad que tiene un usuario 
     * @param token String con el token del usuario
     * @return Lista de invitaciones que tiene el usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<RequestSquad> getInvitations(String token) throws InterruptedException, ExecutionException {
        List<RequestSquad> myInvitationsList = new ArrayList<>();
        Query myInvitations = firebaseCrud.getCollection("InvitacionesSquad").whereEqualTo("receptor",
                firebaseSecAuth.getEmail(token));
        for (DocumentSnapshot doc : myInvitations.get().get().getDocuments()) {
            if (doc != null) {
                myInvitationsList.add(doc.toObject(RequestSquad.class));
            }
        }
        return myInvitationsList;
    }
    /**
     * con este añade al usuario al squad y elimina la solicitud de la base de datos
     * @param token String con el token del usuario
     * @param squad Objeto de tipo squad
     * @param remitenteId Identificador del remitente
     * @return boolean que confirma si se pudo realizar el procedimiento o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean acceptInvite(String token, Squad squad, String remitenteId)
            throws InterruptedException, ExecutionException {
        boolean membersSaved = joinSquad(token, squad);
        boolean invitationDeleted = false;
        if (membersSaved == true) {
            String invitationId = getInvitationId(token, squad.getId_squad(), remitenteId);
            if (invitationId != null) {
                invitationDeleted = firebaseCrud.delete(invitationId, "InvitacionesSquads");
            }
        }
        return membersSaved && invitationDeleted;
    }
    /**
     * 
     * @param token String con el token del usuario
     * @param squadId Identificador del squad
     * @param remitenteId Identificador del remitente
     * @return Invitaciones de un squad en especifico
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    public String getInvitationId(String token, String squadId, String remitenteId)
            throws InterruptedException, ExecutionException {
        List<QueryDocumentSnapshot> allSquadsInvitations = firebaseCrud.getCollection("InvitacionesSquads")
                .whereEqualTo("idSquad", squadId).get().get().getDocuments();
        for (DocumentSnapshot doc : allSquadsInvitations) {
            if (doc != null) {
                RequestSquad aux = doc.toObject(RequestSquad.class);
                if (aux.getRemitente().equals(remitenteId)
                        && aux.getReceptor().equals(firebaseSecAuth.getEmail(token))) {
                    return doc.getId();
                }
            }
        }
        return null;
    }
    /**
     * Une a una persona al squad haciendo uso de su token de autenticación y el objeto squad al que se quiere unir.
     * @param token String con el token del usuario
     * @param squad Objeto de tipo squad
     * @return boolean que confirma si se pudo realizar el metodo o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean joinSquad(String token, Squad squad) throws InterruptedException, ExecutionException {
        Person myself = firebaseCrud.getById("Persona", firebaseSecAuth.getEmail(token)).toObject(Person.class);
        myself.setPersona_id(firebaseSecAuth.getEmail(token));
        int quantityMembers = firebaseCrud.getSubCollection("Squad", squad.getId_squad(), "Integrantes").get().get()
                .getDocuments().size();
        boolean membersSaved = false;
        boolean chatUpdated = false;
        if (myself != null && quantityMembers < 8) {
            membersSaved = firebaseCrud.saveSubCollection("Squad", squad.getId_squad(), "Integrantes", myself);
            if (membersSaved == true) {
                Chat chat = firebaseCrud.getById("Chat", squad.getChat_id()).toObject(Chat.class);
                chat.getIntegrantes().add(firebaseSecAuth.getEmail(token));
                chatUpdated = firebaseCrud.update(squad.getChat_id(), "Chat", chat);
            }
        }
        return membersSaved && chatUpdated;
    }
}
