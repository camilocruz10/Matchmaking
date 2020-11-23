package com.atomiclab.socialgamerbackend;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.ApplicationTest;
import com.atomiclab.socialgamerbackend.domain.model.*;
import com.atomiclab.socialgamerbackend.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
public class CasesTest extends ApplicationTest{

    @Autowired
    ChatService chatService;
    String mockEmail = "camiloru07@gmail.com";
    String mockEmailMain2 = "crackmilo@match.com";
    String mockUsernameMain2 = "crackmilo1999";
    String mockFotoPerfilMain2 = "Fotosperfil/1605942873659-Awesome-Love-Heart-3D-Wallpapers-Art.jpg";
    String mockEmailMain = "crackmilotest10@match.com";
    String mockUsernameMain = "crackmilotest";
    String mockFotoPerfilMain = "Fotosperfil/1605942759382-graicas_faryd.png";
    String mockChatId = "asbcI3I8Js58dq8FPpQp";
    String mockMsjId = "cZADrIicCfQtM2QjxT2i";
    String dateForChats = "2020-11-21 02:17:53";
    List<String> integrantesMock = new ArrayList<>();
    List<Mensaje> mensajesMock = new ArrayList<>();
    String token2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjNlNTQyN2NkMzUxMDhiNDc2NjUyMDhlYTA0YjhjYTZjODZkMDljOTMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc3ByaW5nLWNvdXJzZS1jNGU1YSIsImF1ZCI6InNwcmluZy1jb3Vyc2UtYzRlNWEiLCJhdXRoX3RpbWUiOjE2MDYxNDk1NTUsInVzZXJfaWQiOiJ4YkViaVRQMDlZWnRvakZPQWxZRGlwc3VDTnAyIiwic3ViIjoieGJFYmlUUDA5WVp0b2pGT0FsWURpcHN1Q05wMiIsImlhdCI6MTYwNjE0OTU1NSwiZXhwIjoxNjA2MTUzMTU1LCJlbWFpbCI6ImNyYWNrbWlsbzEwQG1hdGNoLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJjcmFja21pbG8xMEBtYXRjaC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.GGsHgFBTiIcACQhtFEM6KB3dM4kp2b4sk9_2qB0nnm49fOYSKVViecjA6SxVPyg97FhaL97AT4UavaX1N2FwFeZ8M8AlTfSLzUI3vjfJUEbwun073UysBlusoInwsokf-VHZziWbsO4tMwpKov42EKkxjX8zR6GbLNy2X9AeFeONK3Qr5DLuYLx1ZUc6mHxBlOMzht_g2C2KZo_3yjLMOtW5ab4ONskz3iXjctc9JRafT3Og1Wwus1GvLad4aNu_c016ZyGxz6DzkbwLw9Yw33uy3Cdqj7puRsQ2ku7eKYqFTDWXw8_yPYqX1cmqyw7vD8FVsoBucf37BXGjcRMzEA";
    String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjNlNTQyN2NkMzUxMDhiNDc2NjUyMDhlYTA0YjhjYTZjODZkMDljOTMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc3ByaW5nLWNvdXJzZS1jNGU1YSIsImF1ZCI6InNwcmluZy1jb3Vyc2UtYzRlNWEiLCJhdXRoX3RpbWUiOjE2MDYxNDk1MDcsInVzZXJfaWQiOiJBZ0dvZHBFOXVWWVZDeHgwcVVhcE9leTNJU1kyIiwic3ViIjoiQWdHb2RwRTl1VllWQ3h4MHFVYXBPZXkzSVNZMiIsImlhdCI6MTYwNjE0OTUwNywiZXhwIjoxNjA2MTUzMTA3LCJlbWFpbCI6ImNyYWNrbWlsb3Rlc3QxMEBtYXRjaC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiY3JhY2ttaWxvdGVzdDEwQG1hdGNoLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.N4zUZLbWKrlcVjpt4CGfb5igX-pon-LcCKGVyXi97VC3jImx9QQTjbTDW9oHTCpVuvRF6IjpUEE_thuRVCP9iVY0wMC_uuzUHNLAGwlCji6zcfpXR-redw_0ge_L7SUfwUdjXzwwp-VaoZPiuydRQjVIHq1ceEFmagTdVdd1fVxKTjV6hLi0Fo-0W-GrLyCFt1M01xRoW6dLXgy4f-zOjG36a3JDZ6pF1rjuENWtcmedl5Gu7azFvTqBPd3A5_4guYHT355c-0arjhw9G3gvWxXA15q8nZN4-eb_7-AM1p7VrNTqz9Jdg_-1RF3unGNTcJaka94uxyxeChFIIpfpGA";
    @Autowired
    ClanService clanService ;
    String nombreClan = "Clan_prueba";
    @Autowired
    FriendService friendService ;
    String mockEmailMain3 = "crackrequest@match.com";
    String mockUsernameMain3 = "crackreques";
    String mockFotoPerfilMain3 = "Fotosperfil/1605942873659-Awesome-Love-Heart-3D-Wallpapers-Art.jpg";
    String mockEmailMain4 = "camiloru007@gmail.com";
    @Autowired
    GameManageService gameManageService  ;
    String imagenJuegoMock = "Juegos/matchmaking1.png";
    String juegoPrueba = "FIFA PRUEBA";
    @Autowired
    GamesService gameService;
    @Autowired
    MatchmakingService matchmakingService;
    @Autowired
    PostService postService ;
    String idPublicacionMock = "crackmilotest10@match.com2020-11-22T00:09:46.999326500";
    @Autowired
    SearchService searchService ;
    @Autowired
    SquadsService squadsService ;
    @Autowired
    UserManageService userManageService ;
    String tokenAdmin = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjNlNTQyN2NkMzUxMDhiNDc2NjUyMDhlYTA0YjhjYTZjODZkMDljOTMiLCJ0eXAiOiJKV1QifQ";
    String profileToDelete = "profiletodelete@match.com";
    @Autowired
    UserService userService ;
    String fakeToken = "fakeToken";
    String fakeId = "fakeid";
    String fakeName = "fakenaem";

    @org.junit.Test
    public void testGetChats() throws InterruptedException, ExecutionException, ParseException {
        List <Chat> chats = new ArrayList<>();
        Chat chat= new Chat ();
        Mensaje msj =new  Mensaje();
        chat.setId(mockChatId);
        integrantesMock.add(mockEmailMain);
        integrantesMock.add(mockEmailMain2);
        chat.setIntegrantes(integrantesMock);
        msj.setId(mockMsjId);
        msj.setMensaje("!Ya puedes comenzar a chatear con tu amigo");
        msj.setRemitente(mockEmailMain2);
        Date result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse(dateForChats);
        msj.setFechayhora(result);
        mensajesMock.add(msj);
        mensajesMock.clear();
        chat.setMensajes(mensajesMock);
        chat.setUltimomsj(result);
        chats.add(chat);
        integrantesMock.clear();
        assertEquals(chatService.getChats(token), chats);
    }
    @org.junit.Test
    public void testGetChatById() throws InterruptedException, ExecutionException, ParseException {
        Chat chat= new Chat ();
        Mensaje msj =new  Mensaje();
        chat.setId(mockChatId);
        integrantesMock.add(mockEmailMain);
        integrantesMock.add(mockEmailMain2);
        chat.setIntegrantes(integrantesMock);
        msj.setId(mockMsjId);
        msj.setMensaje("!Ya puedes comenzar a chatear con tu amigo");
        msj.setRemitente(mockEmailMain2);
        Date result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse(dateForChats);
        msj.setFechayhora(result);
        mensajesMock.add(msj);
        mensajesMock.clear();
        chat.setMensajes(mensajesMock);
        chat.setUltimomsj(result);
        integrantesMock.clear();
        assertEquals(chatService.getChatById(mockChatId), chat);
    }
    /* 
        Tests for clan
    */
    @org.junit.Test
    public void testgetMembers() throws InterruptedException, ExecutionException {
        List <Person> persons = new ArrayList<>();
        Person person = new Person ();
        person.setPersona_id(mockEmailMain);
        person.setNombre_usuario(mockUsernameMain);
        person.setFoto_perfil("Fotosperfil/1605942759382-graicas_faryd.png");
        persons.add(person);
        assertEquals(clanService.getMembers(nombreClan), persons);
    }
    @org.junit.Test
    public void testGetPosts () throws InterruptedException, ExecutionException, ParseException {
        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain);
        person.setNombre_usuario(mockUsernameMain);
        person.setPersona_id(mockEmailMain);
        post.setPerson(person);
        post.setReportado(false);
        post.setContenido("prueba");
        Date result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse("2020-11-21 02:17:53 ???");
        post.setFecha(result);
        post.setId("dfghj");
        post.setImagen("imagen");
        posts.add(post);
        assertEquals(clanService.getPosts(nombreClan, token), posts);
    }
    
    @org.junit.Test
    public void testGetClans() throws InterruptedException, ExecutionException {
        assertEquals(clanService.getClans().size(), 5);
    }

    @org.junit.Test
    public void testGetMyClans() throws InterruptedException, ExecutionException {
        Clan clan = new Clan ();
        clan.setNombre_clan("Clan_prueba");
        clan.setDescripcion("Este es un clan de prueba");
        clan.setEsPrivado(false);
        clan.setFoto_clan("Clanes/1605948415996-luk.png");
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain);
        person.setNombre_usuario(mockUsernameMain);
        person.setPersona_id(mockEmailMain);
        clan.setPerson(person);
        assertEquals(clanService.getMyClans(token), clan);
    }
    
    @org.junit.Test
    public void testGetRequests() throws InterruptedException, ExecutionException {
        List<Person> persons = new ArrayList<>();
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain2);
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        persons.add(person);
        assertEquals(clanService.getRequests(nombreClan, token), persons);
    }


    /*
     *  DeleteMember 
     */

    @org.junit.Test
    public void testIsMember() throws InterruptedException, ExecutionException {
        assertEquals(clanService.isMember(token, nombreClan), true);
    }

    @org.junit.Test
    public void testIsRequestSend() throws InterruptedException, ExecutionException {
        assertEquals(clanService.isRequestSend(token, nombreClan), false);
    }
    
    @org.junit.Test
    public void testIsAdmin() throws InterruptedException, ExecutionException {
        assertEquals(clanService.isAdmin(token, nombreClan), true);
    }
    /*
     * Friends Requests 
     */
    @org.junit.Test
    public void testGetFriendRequests() throws InterruptedException, ExecutionException {
        List<Person> persons = new ArrayList<>();
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain3);
        person.setNombre_usuario(mockUsernameMain3);
        person.setPersona_id(mockEmailMain3);
        persons.add(person);
        assertEquals(friendService.getFriendRequests(token), persons);
    }

    @org.junit.Test
    public void testIsFriend() throws InterruptedException, ExecutionException {
        assertEquals(friendService.isFriend(token, mockEmailMain2), true);
    }

    @org.junit.Test
    public void testIsRequestSendFriend() throws InterruptedException, ExecutionException {
        assertEquals(friendService.isRequestSend(token, mockEmailMain3), true);
    }

    /**
     * RejectFriend
     */
    

    @org.junit.Test
    public void testGetFriends() throws InterruptedException, ExecutionException {
        List<Person> persons = new ArrayList<>();
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain3);
        person.setNombre_usuario(mockUsernameMain3);
        person.setPersona_id(mockEmailMain3);
        persons.add(person);
        person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain2);
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        persons.add(person);
        assertEquals(friendService.getFriends(token, mockEmailMain), persons);
    }
    /**
     * GameManageService tests
     */

    @org.junit.Test
    public void testGetGame() throws InterruptedException, ExecutionException {
        Games game = new Games();
        game.setImagen(imagenJuegoMock);
        game.setNombre(juegoPrueba);
        assertEquals(gameManageService.getGame(juegoPrueba, token), game);
    }
    


    @org.junit.Test
    public void testIsAdminGameManage() throws InterruptedException, ExecutionException {
        assertEquals(gameManageService.isAdmin(token), false);
    }

    /**
     * GamesServiceTests
     */

    @org.junit.Test
    public void testGetGames() throws InterruptedException, ExecutionException {
        assertEquals(gameService.getGames().size(), 7);
    }


    @org.junit.Test
    public void testGetFavorites() throws InterruptedException, ExecutionException {
        List <Games> games  = new ArrayList<>();
        Games game = new Games();
        game.setImagen(imagenJuegoMock);
        game.setNombre(juegoPrueba);
        games.add(game);
        assertEquals(gameService.getFavorites(mockEmailMain),games);
    }

    @org.junit.Test
    public void testFindMatchMaking() throws InterruptedException, ExecutionException {
        Matchmaking match = new Matchmaking();
        List<String> juegos = new ArrayList<>();
        juegos.add("COD");
        match.setJuegos(juegos);
        match.setRegion("LAN");
        Person person = new Person();
        person.setFoto_perfil(mockFotoPerfilMain);
        person.setNombre_usuario(mockUsernameMain);
        person.setPersona_id(mockEmailMain);
        assertEquals(matchmakingService.findMatch(match, token).size(),1 );
    }
    /**
     * Delete matchmaking
     */

    /*
     * PostService Test
     *  */
        /**
         * SetFeed
         */
    @org.junit.Test
    public void testGetFeed() throws InterruptedException, ExecutionException {
        assertEquals(postService.getFeed(token).size(), 1);
    }

    @org.junit.Test
    public void testGetComments() throws InterruptedException, ExecutionException {
        assertEquals(postService.getComments(idPublicacionMock, token).size(),1);
    }

    @org.junit.Test
    public void testGetLikesF() throws InterruptedException, ExecutionException {
        Integer i = 1;
        assertEquals(postService.getLikes(idPublicacionMock), i);
    }

    @org.junit.Test
    public void testGetPostsPosts() throws InterruptedException, ExecutionException {
        assertEquals(postService.getPosts(mockEmail).size(),1);
    }

    /**
     * SearchServiceImpl
     */

    @org.junit.Test
    public void testSearchUsers() throws InterruptedException, ExecutionException {
        assertEquals(searchService.searchUser("Persona","c").size(),6);
    }

    @org.junit.Test
    public void testSearchGames() throws InterruptedException, ExecutionException {
        assertEquals(searchService.searchUser("Juego","COD").size(),2);
    }
    @org.junit.Test
    public void testSearchPosts() throws InterruptedException, ExecutionException {
        assertEquals(searchService.searchPost("Publicaciones","algo").size(),1);
    }
    @org.junit.Test
    public void testSearchClanes() throws InterruptedException, ExecutionException {
        assertEquals(searchService.searchClan("Clanes","_").size(),1);
    }
    @org.junit.Test
    public void testSearchSquads() throws InterruptedException, ExecutionException {
        assertEquals(searchService.searchSquad("Squad","squad").size(),2);
    }
    
    /**
     * SquadsServiceImpl
     */
    @org.junit.Test
    public void testGetSquad() throws InterruptedException, ExecutionException {
        Squad squad = new Squad ();
        squad.setAdmin(mockEmailMain2);
        squad.setVisibilidad(true);
        squad.setNombre("Nuevo squad");
        squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
        Person person = new Person();
        List<Person> integrantes = new ArrayList<>();
        person.setFoto_perfil("Fotoperfil/1605942759382-graicas_faryd.png");
        person.setNombre_usuario(mockEmailMain2);
        person.setPersona_id(mockUsernameMain2);
        integrantes.add(person);
        squad.setIntegrantes(integrantes);
        assertEquals(squadsService.getSquad("3lAILc7awa6S57laET0T"), squad);
    }
    
    
    @org.junit.Test
    public void testGetIntegrantes() throws InterruptedException, ExecutionException {
        Person person = new Person();
        List<Person> integrantes = new ArrayList<>();
        person.setFoto_perfil("Fotoperfil/1605942759382-graicas_faryd.png");
        person.setNombre_usuario(mockEmailMain2);
        person.setPersona_id(mockUsernameMain2);
        integrantes.add(person);
        assertEquals(squadsService.getIntegrantes("3lAILc7awa6S57laET0T"),integrantes);
    }
    
    @org.junit.Test
    public void testFriendsSquads() throws InterruptedException, ExecutionException {
        Set<Squad> squads = new HashSet<>();
        Squad squad = new Squad ();
        squad.setAdmin(mockEmailMain2);
        squad.setVisibilidad(true);
        squad.setId_squad("3lAILc7awa6S57laET0T");
        squad.setChat_id("DJRb6VULUZIk29y64xXx");
        squad.setNombre("Nuevo squad");
        squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
        Person person = new Person();
        List<Person> integrantes = new ArrayList<>();
        person.setFoto_perfil("Fotoperfil/1605942759382-graicas_faryd.png");
        person.setNombre_usuario(mockEmailMain2);
        person.setPersona_id(mockUsernameMain2);
        integrantes.add(person);
        squad.setIntegrantes(integrantes);
        squads.add(squad);
        assertEquals(squadsService.getFriendsSquads(token),squads);
    }


    @org.junit.Test
    public void testGetSquads() throws InterruptedException, ExecutionException {
        List<Squad> squads = new ArrayList<>();
        Squad squad = new Squad ();
        squad.setAdmin(mockEmailMain2);
        squad.setVisibilidad(true);
        squad.setNombre("Nuevo squad");
        squad.setId_squad("3lAILc7awa6S57laET0T");
        squad.setChat_id("DJRb6VULUZIk29y64xXx");
        squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
        Person person = new Person();
        List<Person> integrantes = new ArrayList<>();
        person.setFoto_perfil("Fotoperfil/1605942759382-graicas_faryd.png");
        person.setNombre_usuario(mockEmailMain2);
        person.setPersona_id(mockUsernameMain2);
        integrantes.add(person);
        squad.setIntegrantes(integrantes);
        squads.add(squad);
        assertEquals(squadsService.getMySquads(token),squads);
    }


    @org.junit.Test
    public void testGetInvitations() throws InterruptedException, ExecutionException {
        assertEquals(squadsService.getInvitations(token).size(),0);
    }


    /**
     * 
     * UserManage
     */
    @org.junit.Test
    public void testUnReportPost() throws InterruptedException, ExecutionException {
        assertEquals(userManageService.unreportPost("crackmilotest10@match.com2020-11-22T00:09:46.999326500", tokenAdmin),true);
    }

    @org.junit.Test
    public void testUnReportProfile() throws InterruptedException, ExecutionException {
        assertEquals(userManageService.unreportPost("crackmilotest10@match.com", tokenAdmin),true);
    }

    @org.junit.Test
    public void testGetReportedPosts() throws InterruptedException, ExecutionException {
        assertEquals(userManageService.getReportedPosts(tokenAdmin).size(),5);
    }

    @org.junit.Test
    public void testGetReportedUsers() throws InterruptedException, ExecutionException {
        assertEquals(userManageService.getReportedUsers(tokenAdmin).size(),2);
    }



    @org.junit.Test
    public void testIsAdminUserManage() throws InterruptedException, ExecutionException {
        assertEquals(userManageService.isAdmin(token),false);
    }

    /**
     * UserService
     * 
     * @throws ParseException
     */



    @org.junit.Test
    public void testGetUser() throws InterruptedException, ExecutionException, ParseException {
        User user=new User();
        user.setNombres("crackmilo");
        user.setApellidos("cruz");
        user.setCorreo("crackmilotest10@match.com");
        user.setConexion(1000);
        Date result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse("2020-11-14 19:00:00");
        user.setFecha_nacimiento(result);
        user.setFoto_perfil("Fotosperfil/1605942759382-graicas_faryd.png");
        user.setJugando("");
        user.setNombre_usuario("crackmilotest");
        List <String> plataformas = new ArrayList<>();
        plataformas.add("PS4");
        user.setPlataformas(plataformas);
        user.setRegion_id("Europa Nórdica y Este");
        user.setReportado(false);
        assertEquals(userService.getUser("crackmilotest10@match.com"), user);
    }

    public void testGetAllUsers() throws InterruptedException, ExecutionException, ParseException {
        assertEquals(userService.getAllUsers().size(), 14);
    }

    @org.junit.Test
    public void testGetUserByToken() throws InterruptedException, ExecutionException, ParseException {
        User user=new User();
        user.setNombres("crackmilo");
        user.setApellidos("cruz");
        user.setCorreo("crackmilotest10@match.com");
        user.setConexion(1000);
        Date result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse("2020-11-14 19:00:00");
        user.setFecha_nacimiento(result);
        user.setFoto_perfil("Fotosperfil/1605942759382-graicas_faryd.png");
        user.setJugando("");
        user.setNombre_usuario("crackmilotest");
        List <String> plataformas = new ArrayList<>();
        plataformas.add("PS4");
        user.setPlataformas(plataformas);
        user.setRegion_id("Europa Nórdica y Este");
        user.setReportado(false);
        assertEquals(userService.getUserByToken(token), user);
    }



    @org.junit.Test
    public void testIsAdminUserService() throws InterruptedException, ExecutionException, ParseException {
        assertEquals(userService.isAdmin(tokenAdmin), true);
    }

    /* Tests that include a create or an addition    
    @org.junit.Test
    public void testCreateChat() throws InterruptedException, ExecutionException {
        Chat chat = new Chat();
        List<String> inte = new ArrayList<>();
        inte.add("camiloru007@gmail.com");
        inte.add("crackmilo10@match.com");
        chat.setIntegrantes(inte);
        assertEquals(chatService.createChat(chat, token), true);
    }

        @org.junit.Test
    public void testCreateClan() throws InterruptedException, ExecutionException {
        Clan clan = new Clan ();
        clan.setNombre_clan("Clan_prueba");
        clan.setDescripcion("Este es un clan de prueba");
        clan.setEsPrivado(true);
        clan.setFoto_clan("1604808042993-facherita.png");
        assertEquals(clanService.createClan(clan, token), true);
    }
    
    */

//@org.junit.Test
//public void testCreateChat() throws InterruptedException, ExecutionException {
//    Chat chat = new Chat();
//    List<String> inte = new ArrayList<>();
//    inte.add("camiloru007@gmail.com");
//    inte.add("crackmilo10@match.com");
//    chat.setIntegrantes(inte);
//    assertEquals(chatService.createChat(chat, token), true);
//}

//@org.junit.Test
//public void testSendMessage() throws InterruptedException, ExecutionException {
//    Mensaje msj = new Mensaje();
//    msj.setFechayhora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//    msj.setMensaje("messaje de prueba");
//    msj.setRemitente(mockEmail);
//    assertEquals(chatService.sendMessage(msj, mockChatId), true);
//}
//@org.junit.Test
//public void updateChat() throws InterruptedException, ExecutionException, ParseException {
//    List <Chat> chats = new ArrayList<>();
//    Chat chat= new Chat ();
//    Mensaje msj =new  Mensaje();
//    chat.setId(mockChatId);
//    integrantesMock.add(mockEmailMain);
//    integrantesMock.add(mockEmailMain2);
//    chat.setIntegrantes(integrantesMock);
//    msj.setId(mockMsjId);
//    msj.setMensaje("!Ya puedes comenzar a chatear con tu amigo!!!!");
//    msj.setRemitente(mockEmailMain2);
//    Date result = null;
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    result  = dateFormat.parse(dateForChats);
//    msj.setFechayhora(result);
//    mensajesMock.add(msj);
//    mensajesMock.clear();
//    chat.setMensajes(mensajesMock);
//    chat.setUltimomsj(result);
//    chats.add(chat);
//    integrantesMock.clear();
//    assertEquals(chatService.updateChat(mockChatId,chat), true);
//}
//@org.junit.Test
//public void testCreateClan() throws InterruptedException, ExecutionException {
//    Clan clan = new Clan ();
//    clan.setNombre_clan("Clan_prueba");
//    clan.setDescripcion("Este es un clan de prueba");
//    clan.setEsPrivado(false);
//    clan.setFoto_clan("Clanes/1605948415996-luk.png");
//    assertEquals(clanService.createClan(clan, token), true);
//}
// error en el codigo
//@org.junit.Test
//public void testAddMemberClan() throws InterruptedException, ExecutionException {
//    Person person = new Person ();
//    person.setFoto_perfil(mockFotoPerfilMain2);
//    person.setNombre_usuario(mockUsernameMain2);
//    person.setPersona_id(mockEmailMain2);
//    assertEquals(clanService.addMember(person, nombreClan, token), true);
//}
//@org.junit.Test
//public void testAddPostClan() throws InterruptedException, ExecutionException {
//    Post post = new Post();
//    post.setContenido("prueba");
//    post.setImagen("imagen");
//    assertEquals(clanService.addPost(post, nombreClan, token), true);
//}
//@org.junit.Test
//public void testRejectRequests() throws InterruptedException, ExecutionException {
//    assertEquals(clanService.rejectRequest(mockEmailMain2, nombreClan,token), true);
//}
// @org.junit.Test
// public void testAddFriend() throws InterruptedException, ExecutionException {
//     assertEquals(friendService.addFriend(token, mockEmailMain3), true);
// }
//@org.junit.Test
//public void testSendFriendRequest() throws InterruptedException, ExecutionException {
//    assertEquals(friendService.sendFriendRequest(token, mockEmailMain4), true);
//}
//@org.junit.Test
//public void testDeleteFriend() throws InterruptedException, ExecutionException {
//    friendService.addFriend(token, mockEmailMain4);
//    assertEquals(friendService.deleteFriend(token, mockEmailMain4), true);
//}
//@org.junit.Test
//public void testCreateGame() throws InterruptedException, ExecutionException {
//    Games game = new Games();
//    game.setImagen(imagenJuegoMock);
//    game.setNombre(juegoPrueba);
//    assertEquals(gameManageService.createGame(game, token), true);
//}
//@org.junit.Test
//public void testUpdateGame() throws InterruptedException, ExecutionException {
//    Games game = new Games();
//    game.setImagen("Juegos/carro.png");
//    game.setNombre("Updated Game");
//    assertEquals(gameManageService.updateGame(juegoPrueba, game, token), true);
//}
//@org.junit.Test
//public void testDeleteGame() throws InterruptedException, ExecutionException {
//    assertEquals(gameManageService.deleteGame("Updated Game", "Juegos/carro.png", token), true);
//}
//@org.junit.Test
//public void testSetGames() throws InterruptedException, ExecutionException {
//    List <Games> games  = new ArrayList<>();
//    Games game = new Games();
//    game.setImagen(imagenJuegoMock);
//    game.setNombre(juegoPrueba);
//    games.add(game);
//    assertEquals(gameService.setGames(token,games), true);
//}
//@org.junit.Test
//public void testUploadPost() throws InterruptedException, ExecutionException {
//    Post post = new Post();
//    post.setContenido("contenido");
//    post.setImagen("Publicaciones/1601513801553-436-don-arbolon-cuentos-para-ninos.jpg");
//    assertEquals(postService.uploadPost(post, token), true);
//}
//@org.junit.Test
//public void testMakeComments() throws InterruptedException, ExecutionException {
//    Comment comment = new Comment();
//    comment.setComentario("comentario prueba");
//    comment.setPublicacion_id(idPublicacionMock);
//    assertEquals(postService.makeComment(token,comment), true);
//}
//@org.junit.Test
//public void testLike() throws InterruptedException, ExecutionException {
//    assertEquals(postService.like(idPublicacionMock, token),true);
//}
//@org.junit.Test
//public void testCreateSquads() throws InterruptedException, ExecutionException {
//    Squad squad = new Squad ();
//    squad.setNombre("prueba squa");
//    squad.setImagen("Squads/Awesome-Love-Heart-3D-Wallpapers-Art.jpg");
//    assertEquals(squadsService.createSquad(squad, token), true);
//}
//
//@org.junit.Test
//public void testUpdateSquads() throws InterruptedException, ExecutionException {
//    Squad squad = new Squad ();
//    squad.setAdmin(mockEmailMain2);
//    squad.setId_squad("3lAILc7awa6S57laET0T");
//    squad.setChat_id("DJRb6VULUZIk29y64xXx");
//    squad.setVisibilidad(true);
//    squad.setNombre("prueba squa updte");
//    squad.setImagen("Squads/Awesome-Love-Heart-3D-Wallpapers-Art.jpg");
//    assertEquals(squadsService.updateSquad(squad), true);
//}
//@org.junit.Test
//public void testDeleteSquads() throws InterruptedException, ExecutionException {
//    assertEquals(squadsService.deleteSquad("3lAILc7awa6S57laET0T"), true);
//}
//@org.junit.Test
//public void testSendInvitations() throws InterruptedException, ExecutionException {
//    List<String> invitations = new ArrayList<>();
//    invitations.add(mockEmailMain3);
//    invitations.add(mockEmailMain4);
//    assertEquals(squadsService.sendInvitations(invitations, token,"Nuevo squad","3lAILc7awa6S57laET0T"),true);
//}
//@org.junit.Test
//public void testExitSquad() throws InterruptedException, ExecutionException {
//    Squad squad = new Squad ();
//    squad.setAdmin(mockEmailMain2);
//    squad.setVisibilidad(true);
//    squad.setNombre("Nuevo squad");
//    squad.setId_squad("3lAILc7awa6S57laET0T");
//    squad.setChat_id("DJRb6VULUZIk29y64xXx");
//    squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
//    Person person = new Person();
//    List<Person> integrantes = new ArrayList<>();
//    person.setFoto_perfil("Fotoperfil/1605942759382-graicas_faryd.png");
//    person.setNombre_usuario(mockEmailMain2);
//    person.setPersona_id(mockUsernameMain2);
//    integrantes.add(person);
//    squad.setIntegrantes(integrantes);
//    assertEquals(squadsService.exitSquad(token, squad),true);
//}
//@org.junit.Test
//public void testAcceptInvite() throws InterruptedException, ExecutionException {
//    Squad squad = new Squad ();
//    squad.setAdmin(mockEmailMain2);
//    squad.setVisibilidad(true);
//    squad.setNombre("Nuevo squad");
//    squad.setId_squad("3lAILc7awa6S57laET0T");
//    squad.setChat_id("DJRb6VULUZIk29y64xXx");
//    squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
//    assertEquals(squadsService.acceptInvite(token,squad,mockEmailMain2),true);
//}
//@org.junit.Test
//public void testJoinSquad() throws InterruptedException, ExecutionException {
//    Squad squad = new Squad ();
//    squad.setAdmin(mockEmailMain2);
//    squad.setVisibilidad(true);
//    squad.setNombre("Nuevo squad");
//    squad.setId_squad("3lAILc7awa6S57laET0T");
//    squad.setChat_id("DJRb6VULUZIk29y64xXx");
//    squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
//    assertEquals(squadsService.joinSquad(token,squad),true);
//}
//@org.junit.Test
//public void testDeletePost() throws InterruptedException, ExecutionException {
//    assertEquals(userManageService.deletePost("crackmilotest10@match.com2020-11-22T00:09:46.999326500", tokenAdmin), true);
//}
//@org.junit.Test
//public void testDeleteProfile() throws InterruptedException, ExecutionException {
//    assertEquals(userManageService.deleteProfile(profileToDelete,tokenAdmin),true);
//}
//@org.junit.Test
//public void testRegister() throws InterruptedException, ExecutionException, ParseException {
//    User user=new User();
//    user.setNombres("Nombre registro");
//    user.setApellidos("Apellido registro");
//    user.setCorreo("usuborrar@gmail.com");
//    user.setConexion(100);
//    Date result;
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    result  = dateFormat.parse("2020-11-21 02:17:53");
//    user.setFecha_nacimiento(result);
//    user.setFoto_perfil("Fotosperfil/default.png");
//    user.setJugando("");
//    user.setNombre_usuario("usuario borrar");
//    List <String> plataformas = new ArrayList<>();
//    plataformas.add("PS4");
//    user.setPlataformas(plataformas);
//    user.setRegion_id("Brasil");
//    assertEquals(userService.register(user),true);
//}
//@org.junit.Test
//public void testUpdateProfile() throws InterruptedException, ExecutionException, ParseException {
//    User user=new User();
//    user.setNombres("Nombre registro updated");
//    user.setApellidos("Apellido registro ipdated");
//    user.setCorreo("usuborrar@gmail.com");
//    user.setConexion(100);
//    Date result;
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    result  = dateFormat.parse("2020-11-21 02:17:53");
//    user.setFecha_nacimiento(result);
//    user.setFoto_perfil("Fotosperfil/default.png");
//    user.setJugando("");
//    user.setNombre_usuario("usuario borrar updated");
//    List <String> plataformas = new ArrayList<>();
//    plataformas.add("PS4");
//    user.setPlataformas(plataformas);
//    user.setRegion_id("Brasil");
//    assertEquals(userService.updateProfile(user,token),true);
//}
//@org.junit.Test
//public void testReportProfile() throws InterruptedException, ExecutionException, ParseException {
//    assertEquals(userService.reportProfile(mockEmailMain3), true);
//}
//@org.junit.Test
//public void testReportPost() throws InterruptedException, ExecutionException, ParseException {
//    assertEquals(userService.reportPost("crackmilotest10@match.com2020-11-22T00:09:46.999326500"), true);
//}
//@org.junit.Test
//public void testAddRequests() throws InterruptedException, ExecutionException {
//    assertEquals(clanService.addRequests(nombreClan, token2), true);
//}
//@org.junit.Test
//public void deleteMember() throws InterruptedException, ExecutionException {
//    assertEquals(clanService.deleteMember(mockEmailMain2, nombreClan, token));
//}

    

}