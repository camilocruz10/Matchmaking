package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.RequestSquad;
import com.atomiclab.socialgamerbackend.domain.model.Squad;
import com.atomiclab.socialgamerbackend.service.SquadsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class SquadsController {
    @Autowired
    SquadsService squadsService;

    @PostMapping("/squads/create")
    public Squad createSquad(@RequestBody Squad squad, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return squadsService.createSquad(squad, token);
    }

    @PostMapping("squads/invitations")
    public boolean sendInvitations(@RequestBody List<String> invitaciones,
            @RequestHeader("X-Firebase-Auth") String token, @RequestHeader("idSquad") String idSquad,
            @RequestHeader("nombreSquad") String nombreSquad) throws InterruptedException, ExecutionException {
        return squadsService.sendInvitations(invitaciones, token, nombreSquad, idSquad);
    }

    @PutMapping("/squads/mysquads/update")
    public boolean updateSquad(@RequestBody Squad squad) throws InterruptedException, ExecutionException {
        return squadsService.updateSquad(squad);
    }

    @DeleteMapping("/squads/delete/{squadName}")
    public boolean deleteSquad(@PathVariable String squadName) throws InterruptedException, ExecutionException {
        return squadsService.deleteSquad(squadName);
    }

    @GetMapping("/squads/{squadId}")
    public Squad getSquad(@PathVariable String squadId) throws InterruptedException, ExecutionException {
        return squadsService.getSquad(squadId);
    }

    @GetMapping("/squads/members/{squadId}")
    public List<Person> getIntegrantes(@PathVariable String squadId) throws InterruptedException, ExecutionException {
        return squadsService.getIntegrantes(squadId);
    }

    @GetMapping("/squads/friends")
    public Set<Squad> getFriendsSquads(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return squadsService.getFriendsSquads(token);
    }

    @GetMapping("/squads/mysquads")
    public List<Squad> getMySquads(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return squadsService.getMySquads(token);
    }

    @PutMapping("/squads/exit")
    public boolean exitSquad(@RequestHeader("X-Firebase-Auth") String token, @RequestBody Squad squad)
            throws InterruptedException, ExecutionException {
        return squadsService.exitSquad(token, squad);
    }

    @GetMapping("squads/invitations")
    public List<RequestSquad> getInvitations(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return squadsService.getInvitations(token);
    }

    @PutMapping("squads/acceptInvite")
    public boolean acceptInvite(@RequestHeader("X-Firebase-Auth") String token, @RequestBody Squad squad,
            @RequestHeader("remitenteId") String remitenteId) throws InterruptedException, ExecutionException {
        return squadsService.acceptInvite(token, squad, remitenteId);
    }

    @PutMapping("squads/join")
    public boolean joinSquad(@RequestHeader("X-Firebase-Auth") String token, @RequestBody Squad squad)
            throws InterruptedException, ExecutionException {
        return squadsService.joinSquad(token, squad);
    }

}
