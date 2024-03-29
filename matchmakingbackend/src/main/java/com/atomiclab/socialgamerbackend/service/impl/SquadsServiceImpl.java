package com.atomiclab.socialgamerbackend.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Chat;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.RequestSquad;
import com.atomiclab.socialgamerbackend.domain.model.Squad;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.repository.FirebaseStorage;
import com.atomiclab.socialgamerbackend.service.ChatService;
import com.atomiclab.socialgamerbackend.service.FriendService;
import com.atomiclab.socialgamerbackend.service.SquadsService;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                collectionIntegrantes.add(personAux);
                persons.add(personAux);
                squad.setIntegrantes(persons);
            }

        }
        return squad;
    }

    public boolean updateSquad(Squad squad) throws InterruptedException, ExecutionException {
        if (squad.getIntegrantes().size() <= 8) {
            return firebaseCrud.update(squad.getId_squad(), "Squad", squad);
        }
        return false;
    }

    public boolean deleteSquad(Squad squad) throws InterruptedException, ExecutionException {
        boolean isChatdeleted = false, isSquadDeleted = false;
        String idMemberToDelete = firebaseCrud.getSubCollection("Squad", squad.getId_squad(), "Integrantes").get()
                .get().getDocuments().get(0).getId();
        firebaseCrud.deleteInSubCollection(squad.getId_squad(), "Squad", idMemberToDelete, "Integrantes");
        isChatdeleted = chatService.deleteChat(squad.getChat_id());
        if (isChatdeleted){
            Query unrespondedInvitations = firebaseCrud.getCollection("InvitacionesSquads").whereEqualTo("idSquad",squad.getId_squad());
            for (DocumentSnapshot doc : unrespondedInvitations.get().get().getDocuments()){
                if (doc != null){
                    doc.getReference().delete();
                }
            }
            firebaseStorage.deleteFile(squad.getImagen());
            isSquadDeleted = firebaseCrud.delete(squad.getId_squad(), "Squad");
        }
        return isChatdeleted && isSquadDeleted;
    }

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
                personAux.setPersona_id(integrante);
                personAuxMyself.setPersona_id(firebaseSecAuth.getEmail(token));
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

    @Override
    public Set<Squad> getFriendsSquads(String token) throws InterruptedException, ExecutionException {
        Set<Squad> friendsSquads = new HashSet<>();
        for (Person friend : friendService.getFriends(token, firebaseSecAuth.getEmail(token))) {
            if (friend != null) {
                friendsSquads.addAll(getFriendSquads(friend.getPersona_id(), firebaseSecAuth.getEmail(token)));
            }
        }
        return friendsSquads;
    }

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

    @Override
    public List<Squad> getMySquads(String token) throws InterruptedException, ExecutionException {
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
            return deleteSquad(squad);
        }

        return delete && updateChat;
    }

    @Override
    public List<RequestSquad> getInvitations(String token) throws InterruptedException, ExecutionException {
        List<RequestSquad> myInvitationsList = new ArrayList<>();
        Query myInvitations = firebaseCrud.getCollection("InvitacionesSquads").whereEqualTo("receptor.persona_id",
                firebaseSecAuth.getEmail(token));
        for (DocumentSnapshot doc : myInvitations.get().get().getDocuments()) {
            if (doc != null) {
                myInvitationsList.add(doc.toObject(RequestSquad.class));
            }
        }
        return myInvitationsList;
    }

    @Override
    public boolean acceptInvite(RequestSquad requestSquad, String token)
    throws InterruptedException, ExecutionException {
        Squad squad = firebaseCrud.getById("Squad", requestSquad.getIdSquad()).toObject(Squad.class);
        boolean membersSaved = joinSquad(token, squad);
        boolean invitationDeleted = false;
        if (membersSaved == true) {
            String invitationId = getInvitationId(token, squad.getId_squad(), requestSquad.getRemitente().getPersona_id());
            if (invitationId != null) {
                invitationDeleted = firebaseCrud.delete(invitationId, "InvitacionesSquads");
            }
        }
        return membersSaved && invitationDeleted;
    }
    @Override
    public boolean declineInvite(RequestSquad requestSquad, String token)
            throws InterruptedException, ExecutionException {
        String invitationId = getInvitationId(token, requestSquad.getIdSquad(), requestSquad.getRemitente().getPersona_id());
        if (invitationId != null) {
            return firebaseCrud.delete(invitationId, "InvitacionesSquads");
        }
        return false;
    }

    public String getInvitationId(String token, String squadId, String remitenteId)
            throws InterruptedException, ExecutionException {
        List<QueryDocumentSnapshot> allSquadsInvitations = firebaseCrud.getCollection("InvitacionesSquads")
                .whereEqualTo("idSquad", squadId).get().get().getDocuments();
        for (DocumentSnapshot doc : allSquadsInvitations) {
            if (doc != null) {
                RequestSquad aux = doc.toObject(RequestSquad.class);
                if (aux.getRemitente().getPersona_id().equals(remitenteId)
                        && aux.getReceptor().getPersona_id().equals(firebaseSecAuth.getEmail(token))) {
                    return doc.getId();
                }
            }
        }
        return null;
    }

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

    @Override
    public boolean kickFromSquad(String personId, Squad squad, String token)
            throws InterruptedException, ExecutionException {
        String idMember = "";
        System.out.println(personId);
        System.out.println(squad.getId_squad());
        if (squad.getAdmin().equals(firebaseSecAuth.getEmail(token))){
            Query query = firebaseCrud.getSubCollection("Squad", squad.getId_squad(), "Integrantes").whereEqualTo("persona_id", personId);
            int quantity = query.get().get().getDocuments().size(); 
            if (quantity > 0){
                idMember = query.get().get().getDocuments().get(0).getId();
                return firebaseCrud.deleteInSubCollection(squad.getId_squad(), "Squad", idMember, "Integrantes");
            }
        }
        return false;
    }
}
