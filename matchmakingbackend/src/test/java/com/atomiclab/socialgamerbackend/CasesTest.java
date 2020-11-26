package com.atomiclab.socialgamerbackend;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    String mockEmail = "camiloru007@gmail.com";
    String mockEmailMain2 = "crackmilo10@match.com";
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
    String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc3ByaW5nLWNvdXJzZS1jNGU1YSIsImF1ZCI6InNwcmluZy1jb3Vyc2UtYzRlNWEiLCJhdXRoX3RpbWUiOjE2MDYzODE0MzAsInVzZXJfaWQiOiJBZ0dvZHBFOXVWWVZDeHgwcVVhcE9leTNJU1kyIiwic3ViIjoiQWdHb2RwRTl1VllWQ3h4MHFVYXBPZXkzSVNZMiIsImlhdCI6MTYwNjM4MTQzMCwiZXhwIjoxNjA2Mzg1MDMwLCJlbWFpbCI6ImNyYWNrbWlsb3Rlc3QxMEBtYXRjaC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiY3JhY2ttaWxvdGVzdDEwQG1hdGNoLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.Zgpi5SCrwvGYldtt2XYFEkSK3ztiaKKn7oW3ncCtRUoFxFOdNnudgG-4Jejqc65fu_ynnWu7V4rHnZUSXyo30dlYZnad2_vwaa0dwHeDdIQo_tkWTn0D2uxEDFtzUI26gSHjLRtrL5wbsBhXKbi_HB2COVqkHsbizwl2HCCoqKCnF3aC4QQXYaPXKy6XRgDr1mudcybiku8f4TfzXEaASBgSumkZZWH9SYgEvDKehpZg678AXo1pfVwTAb7JJ3Uk7GrtTxZBFdIBit4itogIOmTaX2mKM4QTJuG3kXxOR_lgm9-v87alklsbuC9mAybVH5VJzjkCyl8BL00VQF8W1Q";
    String token2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc3ByaW5nLWNvdXJzZS1jNGU1YSIsImF1ZCI6InNwcmluZy1jb3Vyc2UtYzRlNWEiLCJhdXRoX3RpbWUiOjE2MDYzODA4MTMsInVzZXJfaWQiOiJ4YkViaVRQMDlZWnRvakZPQWxZRGlwc3VDTnAyIiwic3ViIjoieGJFYmlUUDA5WVp0b2pGT0FsWURpcHN1Q05wMiIsImlhdCI6MTYwNjM4MDgxMywiZXhwIjoxNjA2Mzg0NDEzLCJlbWFpbCI6ImNyYWNrbWlsbzEwQG1hdGNoLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJjcmFja21pbG8xMEBtYXRjaC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.DCRBlmrp77ychtTXgMgG04AEJt-O-0suianzJCrqoriRptdvcI9Q8FHg8Rw056vO_DBfw62P2F4qCosqmc2t-81UmbLW0KkeEh-ZftK5WMzjQ_APPQmzOl7Tm0UxdtP9YoUfLAuM870nxfyq2PuBYCjDna7DiioEfoHE5LXXf0fXnG7z9MlXmul58Pg1rgQglcNgBLY6f7ftgrqwUedsIeIaghgeGo9ra3UGO5uHQ1GAFIIntNnfIVcL8GatlcaGcZxjLJm1MFRTjWKwMQ95AF7n19UQ7gq5ow2FQeZ_iAaqcnTVE81nC6-9P-Q_4hK5HoxRaD8g-2P11GK3Q0hj1w";
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
    String juegoPrueba = "COD Modern Warface";
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
    String tokenAdmin = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc3ByaW5nLWNvdXJzZS1jNGU1YSIsImF1ZCI6InNwcmluZy1jb3Vyc2UtYzRlNWEiLCJhdXRoX3RpbWUiOjE2MDYzNzUxMTksInVzZXJfaWQiOiJDMmFVNHN1Rjl2ZDU3ZE16NVhxNDkxUkxrdkwyIiwic3ViIjoiQzJhVTRzdUY5dmQ1N2RNejVYcTQ5MVJMa3ZMMiIsImlhdCI6MTYwNjM3NTExOSwiZXhwIjoxNjA2Mzc4NzE5LCJlbWFpbCI6ImNhbWlsb3J1MDA3QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImNhbWlsb3J1MDA3QGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.EsVxecI69rTaIISyrlj6qk5wDkLjy6GxJgVuw2cqaYJwVP49Tecr8HECLwxSnKedYDE9dlKoMN84HO-b39upVM6AbmZHsyT5XvXgD_R_P6tJlra5gASlQq2_cfH9ueTdpCngC3MvbV6-Kv-P5gXQAUxX-TRIgFCKcdKRl31zlcy5IUMvLCeA2J-OJoQdpNEQCvj6Bw1hvc1IKXNwNwapRe9g5l0_zSpjn1lGoVr9ib9TsiRlzo3t45xAlzDf9j__dssAdrPHc2XyE4K9VxfXPe7UWTa4DnEVnhXpFRtgFNDbeaFnaZ9JqGAq1iF8EOsST1fBFFzCwiiBhmc2ktBSBA";
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
        assertEquals(chats.size(),chatService.getChats(token).size());
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
        msj.setRemitente(mockEmailMain);
        Date result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse(dateForChats);
        msj.setFechayhora(result);
        mensajesMock.add(msj);
        chat.setMensajes(mensajesMock);
        mensajesMock.clear();
        chat.setUltimomsj(result);
        integrantesMock.clear();
        assertEquals(chat.getId(),chatService.getChatById(mockChatId).getId());
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
        assertEquals(persons,clanService.getMembers(nombreClan));
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
        post.setContenido("buenaaas");
        Date result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse("2020-11-25 20:33:58");
        post.setFecha(result);
        post.setId("crackmilotest10@match.com2020-11-25T20:33:58.132800800");
        post.setImagen("");
        posts.add(post);
        assertEquals(posts.get(0).getId(),clanService.getPosts(nombreClan, token).get(0).getId());
    }
    
    @org.junit.Test
    public void testGetClans() throws InterruptedException, ExecutionException {
        assertEquals(5,clanService.getClans().size());
    }

    @org.junit.Test
    public void testGetMyClans() throws InterruptedException, ExecutionException {
        List<Clan> clanes = new ArrayList<>();
        Clan clan = new Clan ();
        clan.setNombre_clan("Clan_prueba");
        clan.setDescripcion("Este es un clan de prueba");
        clan.setEsPrivado(true);
        clan.setFoto_clan("Clanes/1605948415996-luk.png");
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain);
        person.setNombre_usuario(mockUsernameMain);
        person.setPersona_id(mockEmailMain);
        clan.setPerson(person);
        clanes.add(clan);
        assertEquals(clanes,clanService.getMyClans(token));
    }
    
    @org.junit.Test
    public void testGetRequests() throws InterruptedException, ExecutionException {
        List<Person> persons = new ArrayList<>();
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain2);
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        persons.add(person);
        assertEquals(0,clanService.getRequests(nombreClan, token).size());
    }


    /*
     *  DeleteMember 
     */

    @org.junit.Test
    public void testIsMember() throws InterruptedException, ExecutionException {
        assertEquals(true, clanService.isMember(token, nombreClan));
    }

    @org.junit.Test
    public void testIsRequestSend() throws InterruptedException, ExecutionException {
        assertEquals(false,clanService.isRequestSend(token, nombreClan));
    }
    
    @org.junit.Test
    public void testIsAdmin() throws InterruptedException, ExecutionException {
        assertEquals(true, clanService.isAdmin(token, nombreClan));
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
        assertEquals(persons.get(0).getPersona_id(),friendService.getFriendRequests(token).get(0).getPersona_id());
    }

    @org.junit.Test
    public void testIsFriend() throws InterruptedException, ExecutionException {
        assertEquals(true,friendService.isFriend(token, mockEmailMain2));
    }

    @org.junit.Test
    public void testIsRequestSendFriend() throws InterruptedException, ExecutionException {
        assertEquals(false, friendService.isRequestSend(token, mockEmailMain3));
    }

    /**
     * RejectFriend
     */
    

    @org.junit.Test
    public void testGetFriends() throws InterruptedException, ExecutionException {
        List<Person> persons = new ArrayList<>();
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain2);
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        persons.add(person);
        assertEquals(persons,friendService.getFriends(token, mockEmailMain));
    }
    /**
     * GameManageService tests
     */

    @org.junit.Test
    public void testGetGame() throws InterruptedException, ExecutionException {
        Games game = new Games();
        game.setImagen("Juegos/1602371335549-codmw.jpg");
        game.setNombre("COD Modern Warface");
        assertEquals(game,gameManageService.getGame(juegoPrueba, tokenAdmin));
    }
    


    @org.junit.Test
    public void testIsAdminGameManage() throws InterruptedException, ExecutionException {
        assertEquals(false,gameManageService.isAdmin(token));
    }

    /**
     * GamesServiceTests
     */

    @org.junit.Test
    public void testGetGames() throws InterruptedException, ExecutionException {
        assertEquals(7,gameService.getGames().size());
    }


    @org.junit.Test
    public void testGetFavorites() throws InterruptedException, ExecutionException {
        List <Games> games  = new ArrayList<>();
        Games game = new Games();
        game.setImagen("Juegos/1602371335549-codmw.jpg");
        game.setNombre("COD Modern Warface");
        games.add(game);
        game.setImagen("Juegos/1602371345018-fifa21.jpg");
        game.setNombre("FIFA 21");
        games.add(game);
        assertEquals(games.get(0).getNombre(), gameService.getFavorites(mockEmailMain).get(0).getNombre());
    }        



    @org.junit.Test
    public void testFindMatchMaking() throws InterruptedException, ExecutionException {
        Matchmaking match = new Matchmaking();
        List<String> juegos = new ArrayList<>();
        juegos.add("COD Modern Warface");
        match.setJuegos(juegos);
        match.setRegion("LAN");
        Person person = new Person();
        person.setFoto_perfil(mockFotoPerfilMain);
        person.setNombre_usuario(mockUsernameMain);
        person.setPersona_id(mockEmailMain);
        List<String> plataformas = new ArrayList<>();
        plataformas.add("PS4");
        match.setPlataformas(plataformas);
        assertEquals(0,matchmakingService.findMatch(match, token).size());
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
        assertEquals(1,postService.getFeed(token).size());
    }

    @org.junit.Test
    public void testGetComments() throws InterruptedException, ExecutionException {
        assertEquals(1,postService.getComments(idPublicacionMock, token).size());
    }

    @org.junit.Test
    public void testGetLikesF() throws InterruptedException, ExecutionException {
        Integer i = 1;
        assertEquals(i,postService.getLikes(idPublicacionMock));
    }

    @org.junit.Test
    public void testGetPostsPosts() throws InterruptedException, ExecutionException {
        assertEquals(20,postService.getPosts(mockEmail).size());
    }

    /**
     * SearchServiceImpl
     */

    @org.junit.Test
    public void testSearchUsers() throws InterruptedException, ExecutionException {
        assertEquals(7,searchService.searchUser("Persona","c").size());
    }

    @org.junit.Test
    public void testSearchGames() throws InterruptedException, ExecutionException {
        assertEquals(2,searchService.searchGames("Juego","COD").size());
    }
    @org.junit.Test
    public void testSearchPosts() throws InterruptedException, ExecutionException {
        assertEquals(1,searchService.searchPost("Publicaciones","algo").size());
    }
    @org.junit.Test
    public void testSearchClanes() throws InterruptedException, ExecutionException {
        assertEquals(1,searchService.searchClan("Clanes","_").size());
    }
    @org.junit.Test
    public void testSearchSquads() throws InterruptedException, ExecutionException {
        assertEquals(3,searchService.searchSquad("Squad","squad").size());
    }
    
    /**
     * SquadsServiceImpl
     */
    @org.junit.Test
    public void testGetSquad() throws InterruptedException, ExecutionException {
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
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        integrantes.add(person);
        squad.setIntegrantes(integrantes);
        assertEquals(squad,squadsService.getSquad("3lAILc7awa6S57laET0T"));
    }
    
    
    @org.junit.Test
    public void testGetIntegrantes() throws InterruptedException, ExecutionException {
        Person person = new Person();
        List<Person> integrantes = new ArrayList<>();
        person.setFoto_perfil(mockFotoPerfilMain2);
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        integrantes.add(person);
        assertEquals(integrantes.get(0).getPersona_id(),squadsService.getIntegrantes("3lAILc7awa6S57laET0T").get(0).getPersona_id());
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
        assertEquals(squads,squadsService.getFriendsSquads(token));
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
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        integrantes.add(person);
        squad.setIntegrantes(integrantes);
        squads.add(squad);
        assertEquals(squads,squadsService.getMySquads(token2));
    }


    @org.junit.Test
    public void testGetInvitations() throws InterruptedException, ExecutionException {
        assertEquals(0,squadsService.getInvitations(token).size());
    }


    /**
     * 
     * UserManage
     */
    @org.junit.Test
    public void testUnReportPost() throws InterruptedException, ExecutionException {
        assertEquals(true,userManageService.unreportPost("crackmilotest10@match.com2020-11-22T00:09:46.999326500", tokenAdmin));
    }

    @org.junit.Test
    public void testUnReportProfile() throws InterruptedException, ExecutionException {
        assertEquals(true,userManageService.unreportPost("crackmilotest10@match.com2020-11-22T00:09:46.999326500", tokenAdmin));
    }

    @org.junit.Test
    public void testGetReportedPosts() throws InterruptedException, ExecutionException {
        assertEquals(5, userManageService.getReportedPosts(tokenAdmin).size());
    }

    @org.junit.Test
    public void testGetReportedUsers() throws InterruptedException, ExecutionException {
        assertEquals(2,userManageService.getReportedUsers(tokenAdmin).size());
    }



    @org.junit.Test
    public void testIsAdminUserManage() throws InterruptedException, ExecutionException {
        assertEquals(false,userManageService.isAdmin(token));
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
        assertEquals(14, userService.getAllUsers().size());
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
        assertEquals(user,userService.getUserByToken(token));
    }



    @org.junit.Test
    public void testIsAdminUserService() throws InterruptedException, ExecutionException, ParseException {
        assertEquals(true,userService.isAdmin(tokenAdmin));
    }
    @org.junit.Test
    public void testIsNotAdminUserService() throws InterruptedException, ExecutionException, ParseException {
        assertEquals(false,userService.isAdmin(token));
    }
    /* Tests that include a create or an addition    
    @org.junit.Test
    public void testCreateChat() throws InterruptedException, ExecutionException {
        Chat chat = new Chat();
        List<String> inte = new ArrayList<>();
        inte.add("camiloru007@gmail.com");
        inte.add("crackmilo10@match.com");
        chat.setIntegrantes(inte);
        assertEquals(true,chatService.createChat(chat, token));
    }

        @org.junit.Test
    public void testCreateClan() throws InterruptedException, ExecutionException {
        Clan clan = new Clan ();
        clan.setNombre_clan("Clan_prueba");
        clan.setDescripcion("Este es un clan de prueba");
        clan.setEsPrivado(true);
        clan.setFoto_clan("1604808042993-facherita.png");
        assertEquals(true,clanService.createClan(clan, token));
    }
    
    */

    @org.junit.Test
    public void testCreateChat() throws InterruptedException, ExecutionException {
        Chat chat = new Chat();
        List<String> inte = new ArrayList<>();
        inte.add("camiloru007@gmail.com");
        inte.add("crackmilo10@match.com");
        chat.setIntegrantes(inte);
        assertEquals(true, chatService.createChat(chat, token));
    }

    @org.junit.Test
    public void testSendMessage() throws InterruptedException, ExecutionException {
        Mensaje msj = new Mensaje();
        msj.setFechayhora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        msj.setMensaje("messaje de prueba");
        msj.setRemitente(mockEmail);
        assertEquals(true,chatService.sendMessage(msj, mockChatId));
    }
    @org.junit.Test
    public void updateChat() throws InterruptedException, ExecutionException, ParseException {
        List <Chat> chats = new ArrayList<>();
        Chat chat= new Chat ();
        Mensaje msj =new  Mensaje();
        chat.setId(mockChatId);
        integrantesMock.add(mockEmailMain);
        integrantesMock.add(mockEmailMain2);
        chat.setIntegrantes(integrantesMock);
        msj.setId(mockMsjId);
        msj.setMensaje("!Ya puedes comenzar a chatear con tu amigo!!!!");
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
        assertEquals(true,chatService.updateChat(mockChatId,chat));
    }
    @org.junit.Test
    public void testCreateClan() throws InterruptedException, ExecutionException {
        Clan clan = new Clan ();
        clan.setNombre_clan("Clan_prueba");
        clan.setDescripcion("Este es un clan de prueba");
        clan.setEsPrivado(false);
        clan.setFoto_clan("Clanes/1605948415996-luk.png");
        assertEquals(true,clanService.createClan(clan, token));
    }
    @org.junit.Test
    public void testAddMemberClan() throws InterruptedException, ExecutionException {
        Person person = new Person ();
        person.setFoto_perfil(mockFotoPerfilMain2);
        person.setNombre_usuario(mockUsernameMain2);
        person.setPersona_id(mockEmailMain2);
        assertEquals(true,clanService.addMember(person, nombreClan, token));
    }
    @org.junit.Test
    public void testAddPostClan() throws InterruptedException, ExecutionException {
        Post post = new Post();
        post.setContenido("prueba");
        post.setImagen("imagen");
        assertEquals(true,clanService.addPost(post, nombreClan, token));
    }
    @org.junit.Test
    public void testRejectRequests() throws InterruptedException, ExecutionException {
        assertEquals(true,clanService.rejectRequest(mockEmailMain2, nombreClan,token));
    }
     @org.junit.Test
     public void testAddFriend() throws InterruptedException, ExecutionException {
         assertEquals(true,friendService.addFriend(token, mockEmailMain3));
     }
    @org.junit.Test
    public void testSendFriendRequest() throws InterruptedException, ExecutionException {
        assertEquals(true,friendService.sendFriendRequest(token, mockEmailMain4));
    }
    @org.junit.Test
    public void testDeleteFriend() throws InterruptedException, ExecutionException {
        friendService.addFriend(token, mockEmailMain4);
        assertEquals(true,friendService.deleteFriend(token, mockEmailMain4));
    }
    @org.junit.Test
    public void testCreateGame() throws InterruptedException, ExecutionException {
        Games game = new Games();
        game.setImagen(imagenJuegoMock);
        game.setNombre(juegoPrueba);
        assertEquals(true,gameManageService.createGame(game, token));
    }
    @org.junit.Test
    public void testUpdateGame() throws InterruptedException, ExecutionException {
        Games game = new Games();
        game.setImagen("Juegos/carro.png");
        game.setNombre("Updated Game");
        assertEquals(true,gameManageService.updateGame(juegoPrueba, game, tokenAdmin);
    }
    @org.junit.Test
    public void testDeleteGame() throws InterruptedException, ExecutionException {
        assertEquals(true,gameManageService.deleteGame("Updated Game", "Juegos/carro.png", token));
    }
    @org.junit.Test
    public void testSetGames() throws InterruptedException, ExecutionException {
        List <Games> games  = new ArrayList<>();
        Games game = new Games();
        game.setImagen(imagenJuegoMock);
        game.setNombre(juegoPrueba);
        games.add(game);
        assertEquals(true,gameService.setGames(token,games));
    }
    @org.junit.Test
    public void testUploadPost() throws InterruptedException, ExecutionException {
        Post post = new Post();
        post.setContenido("contenido");
        post.setImagen("Publicaciones/1601513801553-436-don-arbolon-cuentos-para-ninos.jpg");
        assertEquals(true,postService.uploadPost(post, token));
    }
    @org.junit.Test
    public void testMakeComments() throws InterruptedException, ExecutionException {
        Comment comment = new Comment();
        comment.setComentario("comentario prueba");
        comment.setPublicacion_id(idPublicacionMock);
        assertEquals(true,postService.makeComment(token,comment));
    }
    @org.junit.Test
    public void testLike() throws InterruptedException, ExecutionException {
        assertEquals(true,postService.like(idPublicacionMock, token));
    }
    @org.junit.Test
    public void testCreateSquads() throws InterruptedException, ExecutionException {
        Squad squad = new Squad ();
        squad.setNombre("prueba squa");
        squad.setImagen("Squads/Awesome-Love-Heart-3D-Wallpapers-Art.jpg");
        assertEquals(squad,squadsService.createSquad(squad, token));
    }
    
    @org.junit.Test
    public void testUpdateSquads() throws InterruptedException, ExecutionException {
        Squad squad = new Squad ();
        squad.setAdmin(mockEmailMain2);
        squad.setId_squad("3lAILc7awa6S57laET0T");
        squad.setChat_id("DJRb6VULUZIk29y64xXx");
        squad.setVisibilidad(true);
        squad.setNombre("prueba squa update");
        squad.setImagen("Squads/Awesome-Love-Heart-3D-Wallpapers-Art.jpg");
        assertEquals(true,squadsService.updateSquad(squad));
    }
    @org.junit.Test
    public void testExitSquad() throws InterruptedException, ExecutionException {
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
        assertEquals(true,squadsService.exitSquad(token, squad));
    }
    @org.junit.Test
    public void testDeleteSquads() throws InterruptedException, ExecutionException {
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
        assertEquals(true,squadsService.deleteSquad(squad));
    }
    @org.junit.Test
    public void testSendInvitations() throws InterruptedException, ExecutionException {
        List<String> invitations = new ArrayList<>();
        invitations.add(mockEmailMain3);
        invitations.add(mockEmailMain4);
        assertEquals(true,squadsService.sendInvitations(invitations, token,"Nuevo squad","3lAILc7awa6S57laET0T"));
    }

    @org.junit.Test
    public void testAcceptInvite() throws InterruptedException, ExecutionException {
        Squad squad = new Squad ();
        squad.setAdmin(mockEmailMain2);
        squad.setVisibilidad(true);
        squad.setNombre("Nuevo squad");
        squad.setId_squad("4nwFhMzKd9HILWpmo5RZ");
        squad.setChat_id("DJRb6VULUZIk29y64xXx");
        squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
        RequestSquad reques = new RequestSquad ();
        reques.setNombreSquad("Nuevo squad");
        Person person = new Person();
        person.setPersona_id(mockEmailMain2);
        person.setNombre_usuario(mockUsernameMain);
        person.setFoto_perfil("Fotosperfil/1605942759382-graicas_faryd.png");
        reques.setRemitente(person);
        assertEquals(true,squadsService.acceptInvite(reques, token2));
    }
    @org.junit.Test
    public void testDeclineInvite() throws InterruptedException, ExecutionException {
        RequestSquad reques = new RequestSquad ();
        reques.setIdSquad("4nwFhMzKd9HILWpmo5RZ");
        reques.setNombreSquad("Nuevo squad");
        Person person = new Person();
        person.setPersona_id(mockEmailMain2);
        person.setNombre_usuario(mockUsernameMain);
        person.setFoto_perfil("Fotosperfil/1605942759382-graicas_faryd.png");
        reques.setRemitente(person);
        assertEquals(true,squadsService.declineInvite(reques, token2));
    }
    @org.junit.Test
    public void testJoinSquad() throws InterruptedException, ExecutionException {
        Squad squad = new Squad ();
        squad.setAdmin(mockEmailMain2);
        squad.setVisibilidad(true);
        squad.setNombre("Nuevo squad");
        squad.setId_squad("3lAILc7awa6S57laET0T");
        squad.setChat_id("DJRb6VULUZIk29y64xXx");
        squad.setImagen("Squads/1605839377349-Captura_de_pantalla_de_2020-11-19_21-29-22.png");
        assertEquals(true,squadsService.joinSquad(token,squad));
    }
    @org.junit.Test
    public void testDeletePost() throws InterruptedException, ExecutionException {
        assertEquals(true,userManageService.deletePost("crackmilotest10@match.com2020-11-22T00:09:46.999326500", tokenAdmin));
    }
    @org.junit.Test
    public void testDeleteProfile() throws InterruptedException, ExecutionException {
        assertEquals(true,userManageService.deleteProfile(profileToDelete,tokenAdmin));
    }
    @org.junit.Test
    public void testRegister() throws InterruptedException, ExecutionException, ParseException {
        User user=new User();
        user.setNombres("Nombre registro");
        user.setApellidos("Apellido registro");
        user.setCorreo("usuborrar@gmail.com");
        user.setConexion(100);
        Date result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse("2020-11-21 02:17:53");
        user.setFecha_nacimiento(result);
        user.setFoto_perfil("Fotosperfil/default.png");
        user.setJugando("");
        user.setNombre_usuario("usuario borrar");
        List <String> plataformas = new ArrayList<>();
        plataformas.add("PS4");
        user.setPlataformas(plataformas);
        user.setRegion_id("Brasil");
        assertEquals(true,userService.register(user));
    }
    @org.junit.Test
    public void testUpdateProfile() throws InterruptedException, ExecutionException, ParseException {
        User user=new User();
        user.setNombres("Nombre registro updated");
        user.setApellidos("Apellido registro ipdated");
        user.setCorreo("usuborrar@gmail.com");
        user.setConexion(100);
        Date result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result  = dateFormat.parse("2020-11-21 02:17:53");
        user.setFecha_nacimiento(result);
        user.setFoto_perfil("Fotosperfil/default.png");
        user.setJugando("");
        user.setNombre_usuario("usuario borrar updated");
        List <String> plataformas = new ArrayList<>();
        plataformas.add("PS4");
        user.setPlataformas(plataformas);
        user.setRegion_id("Brasil");
        assertEquals(true,userService.updateProfile(user,token));
    }
    @org.junit.Test
    public void testReportProfile() throws InterruptedException, ExecutionException, ParseException {
        assertEquals(true,userService.reportProfile(mockEmailMain3));
    }
    @org.junit.Test
    public void testReportPost() throws InterruptedException, ExecutionException, ParseException {
        assertEquals(true,userService.reportPost("crackmilotest10@match.com2020-11-22T00:09:46.999326500"));
    }
    @org.junit.Test
    public void testAddRequests() throws InterruptedException, ExecutionException {
        assertEquals(true,clanService.addRequests(nombreClan, token2));
    }

    @org.junit.Test
    public void testKickFromSquad() throws InterruptedException, ExecutionException {
        Squad squad = new Squad();
        assertEquals(false,squadsService.kickFromSquad("fyb85bKUFoyurCFDuVCP", squad, token));
    }
}