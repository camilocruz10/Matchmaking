package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Person;
import com.atomiclab.socialgamerbackend.domain.model.RequestSquad;
import com.atomiclab.socialgamerbackend.domain.model.Squad;

import org.springframework.stereotype.Service;

@Service
public interface SquadsService {
        public Squad createSquad(Squad squad, String token) throws InterruptedException, ExecutionException;

        public boolean sendInvitations(List<String> integrantes, String token, String nombreSquad, String idSquad)
                        throws InterruptedException, ExecutionException;

        public boolean updateSquad(Squad squad) throws InterruptedException, ExecutionException;

        public boolean deleteSquad(Squad squad) throws InterruptedException, ExecutionException;

        public Squad getSquad(String squadId) throws InterruptedException, ExecutionException;

        public List<Person> getIntegrantes(String squadId) throws InterruptedException, ExecutionException;

        public Set<Squad> getFriendsSquads(String token) throws InterruptedException, ExecutionException;

        public List<Squad> getMySquads(String token) throws InterruptedException, ExecutionException;

        public boolean exitSquad(String token, Squad squad) throws InterruptedException, ExecutionException;

        public List<RequestSquad> getInvitations(String token) throws InterruptedException, ExecutionException;

        public boolean acceptInvite(RequestSquad requestSquad, String token)
                        throws InterruptedException, ExecutionException;

        public boolean joinSquad(String token, Squad squad) throws InterruptedException, ExecutionException;

        public boolean declineInvite(RequestSquad requestSquad, String token)
                        throws InterruptedException, ExecutionException;

        public boolean kickFromSquad(String personId, Squad squad, String token)throws InterruptedException, ExecutionException;
}
