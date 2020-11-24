package com.atomiclab.socialgamerbackend.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Clan;
import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.repository.FirebaseCrud;
import com.atomiclab.socialgamerbackend.repository.FirebaseSecAuth;
import com.atomiclab.socialgamerbackend.service.ClanService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query.Direction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Este servicio maneja la lógica de los clanes, que son grupos públicos de usuarios con finalidades personalizadas por ellos.
 * @author Atomic Lab
 * @version 1.0
 */
@Service
public class ClanServiceImpl implements ClanService {
    @Autowired
    FirebaseCrud firebaseCrud;
    @Autowired
    FirebaseSecAuth firebaseSecAuth;
    /**
     * Recibe un objeto Clan y un token de autenticación para agregar a quien será su administrador, finalmente se agrega el nuevo clan a la colección.
     * @param clan Objeto de tipo clan
     * @param token String del token del usuario
     * @return boolean que retorna si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean createClan(Clan clan, String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        person.setPersona_id(correo);
        clan.setPerson(person);
        firebaseCrud.save(clan.getNombre_clan(), "Clanes", clan);
        return firebaseCrud.saveSubCollection("Clanes", clan.getNombre_clan(),"Miembros", person);
    }
    /**
     * Recibe el nombre del clan y retorna la lista de sus miembros.
     * @param nombre_clan nombre del clan
     * @return Lista de personas que son miembros a un clan
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Person> getMembers(String nombre_clan) throws InterruptedException, ExecutionException {
        List<Person> members = new ArrayList<Person>();
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Miembros");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            members.add(document2.toObject(Person.class));
        }
        return members;
    }
    /**
     * Recibe el nombre del clan y un token de autenticación para validar que la persona sea miembro, finalmente retorna la lista de las publicaciones hechas en el grupo.
     * @param nombre_clan nombre del clan
     * @param token String del token del usuario
     * @return Lista de los posts de un clan
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Post> getPosts(String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isMember(token, nombre_clan)) return null;
        List<Post> feed = new ArrayList<Post>();
        CollectionReference feedRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Feed");
        Query feedQuery = feedRef.orderBy("fecha", Direction.DESCENDING).limit(20);
        ApiFuture<QuerySnapshot> querySnapshot = feedQuery.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            feed.add(document2.toObject(Post.class));
        }
        return feed;
    }
    /**
     * Funcion para añadir miembro a un clan
     * @param person Objeto d tipo persona la cuál se va a invitar
     * @param nombre_clan nombre del clan al que se va a invitar
     * @param token String del token del usuario
     * @return boolean que retorna si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean addMember(Person person, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        //rejectRequest validate if isAdmin
        if(!rejectRequest(person.getPersona_id(), nombre_clan, token)) return false;
        return firebaseCrud.saveSubCollection("Clanes", nombre_clan, "Miembros", person);
    }
    /**
     * Recibe un objeto post, el nombre del clan y un token de autenticación para validar que la persona sea miembro, finalmente se añade la publicación al grupo.
     * @param post Objeto de tipo post para agregar al clan
     * @param nombre_clan Nombre del clan el cual se va a agregar el post
     * @param token String del token del usuario
     * @return boolean que retorna si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean addPost(Post post, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isMember(token, nombre_clan)) return false;
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        String id = correo.concat(LocalDateTime.now().toString());
        person.setPersona_id(correo);
        post.setPerson(person);
        post.setFecha(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        post.setReportado(false);
        post.setId(id);
        return firebaseCrud.saveSubCollection("Clanes", nombre_clan, "Feed", post);
    }
    /**
     *  Obtiene la lista de todos los grupos en la base de datos.
     * @return Lista de todos los clanes del sistema
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Clan> getClans() throws InterruptedException, ExecutionException {
        List<Clan> clans = new ArrayList<Clan>();
        CollectionReference clansRef = firebaseCrud.getCollection("Clanes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            clans.add(document2.toObject(Clan.class));
        }
        return clans;
    }
    /**
     * Recibe el token de autenticación, con esto retorna la lista de clanes a los que pertenece la persona.
     * @param token String del tokem del usuario
     * @return Lista de clanes de un usuario en especifico
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Clan> getMyClans(String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        List<Clan> clans = new ArrayList<Clan>();
        return clans;
    }
    /**
     * Recibe el nombre del clan y un token de autenticación para validar que la persona sea el administrador del clan, finalmente se retorna la lista con todas las solicitudes para unirse.
     * @param nombre_clan nombre del clan
     * @param token String del token del usuario
     * @return Retorna lista de usuarios que quieren unirse al clan
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public List<Person> getRequests(String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isAdmin(token, nombre_clan)) return null;
        List<Person> requests = new ArrayList<Person>();
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Solicitudes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            requests.add(document2.toObject(Person.class));
        }
        return requests;
    }
    /**
     * Recibe el identificador del usuario que mandó la solicitud, el nombre del clan y un token de autenticación para validar que la persona sea el administrador del clan, finalmente se rechaza la petición eliminándola de la base de datos.
     * @param person_id identificador de la persona que se va a rechazar
     * @param nombre_clan nombre del clan del cual se rechaza
     * @param token String del token del usuario
     * @return boolean que retorna si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean rejectRequest(String person_id, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isAdmin(token, nombre_clan)) return false;
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Solicitudes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", person_id).get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            document2.getReference().delete();
        }
        return true;
    }
    /**
     * Recibe el identificador del usuario actualmente miembro del clan, el nombre del clan y un token de autenticación para validar que la persona sea el administrador del clan, finalmente se elimina al usuario de la colección de miembros del clan.
     * @param person_id identificador de la persona que se quiere eliminar
     * @param nombre_clan nombre del clan del cual se va a eliminar
     * @param token String del token del usuario
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public void deleteMember(String person_id, String nombre_clan, String token) throws InterruptedException, ExecutionException {
        if(!isAdmin(token, nombre_clan)) return;
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Miembros");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", person_id).get();
        for (DocumentSnapshot document2 : querySnapshot.get().getDocuments()) {
            document2.getReference().delete();
        }
    }
    /**
     * Recibe el nombre del clan y un token de autenticación, con esto valida que el usuario sea miembro del clan.
     * @param token String del token del usuario
     * @param nombre_clan nombre del clan que se quiere verificar
     * @return boolean que confirma si es miembro o no del clan
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean isMember(String token, String nombre_clan) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Miembros");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", correo).get();
        System.out.println(querySnapshot.get().getDocuments().size());
        return querySnapshot.get().getDocuments().isEmpty()? false : true;
    }
    /**
     * Recibe el nombre del clan y un token de autenticación, con esto valida que el usuario ya envió antes una solicitud para unirse.
     * @param token String del token del usuario
     * @param nombre_clan nombre del clan
     * @return boolean que retorna si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean isRequestSend(String token, String nombre_clan) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        CollectionReference clansRef = firebaseCrud.getSubCollection("Clanes", nombre_clan, "Solicitudes");
        ApiFuture<QuerySnapshot> querySnapshot = clansRef.whereEqualTo("persona_id", correo).get();
        return querySnapshot.get().getDocuments().isEmpty()? false : true;
    }
    /**
     * Recibe el nombre del clan y un token de autenticación, con esto valida que el usuario sea administrador del clan.
     * @param token String del token del usuario
     * @param nombre_clan nombre del clan para verificar
     * @return boolean que retorna si es administrador o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean isAdmin(String token, String nombre_clan) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        DocumentSnapshot clansRef = firebaseCrud.getById("Clanes", nombre_clan);
        Person admin = clansRef.toObject(Clan.class).getPerson();
        System.out.println(admin.getPersona_id());
        return admin.getPersona_id().equals(correo)? true : false;
    }
    /**
     * Recibe el nombre del clan y un token de autenticación, finalmente añade a la sub-colección de solicitudes para unirse al clan a la persona identificada con ese token.
     * @param nombre_clan nombre del clan del que se quiere verificar
     * @param token String del token del usuario
     * @return boolean que retorna si se realizo el metodo con exito o no
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @Override
    public boolean addRequests(String nombre_clan, String token) throws InterruptedException, ExecutionException {
        String correo = firebaseSecAuth.getEmail(token);
        Person person = firebaseCrud.getById("Persona", correo).toObject(Person.class);
        person.setPersona_id(correo);
        return firebaseCrud.saveSubCollection("Clanes", nombre_clan, "Solicitudes", person);
    }
}
