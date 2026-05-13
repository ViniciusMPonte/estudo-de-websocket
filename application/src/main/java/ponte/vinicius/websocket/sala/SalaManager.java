package ponte.vinicius.websocket.sala;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class SalaManager {

    private final Map<String, Set<String>> salas = new ConcurrentHashMap<>();
    private final Map<String, String> sessaoPorSala = new ConcurrentHashMap<>();
    private final Map<String, String> uuidPorSessao = new ConcurrentHashMap<>();

    public void entrar(String chave, String sessionId, String uuid) {
        salas.computeIfAbsent(chave, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
        sessaoPorSala.put(sessionId, chave);
        uuidPorSessao.put(sessionId, uuid);
    }

    public void sair(String sessionId) {
        String chave = sessaoPorSala.remove(sessionId);
        if (chave != null) {
            Set<String> membros = salas.get(chave);
            if (membros != null) {
                membros.remove(sessionId);
                if (membros.isEmpty()) salas.remove(chave);
            }
        }
        uuidPorSessao.remove(sessionId);
    }

    public String getSalaDoCliente(String sessionId) {
        return sessaoPorSala.get(sessionId);
    }

    public String getUuid(String sessionId) {
        return uuidPorSessao.get(sessionId);
    }

    public Set<String> getUuidsMembrosExceto(String chave, String sessionIdRemetente) {
        Set<String> membros = salas.getOrDefault(chave, ConcurrentHashMap.newKeySet());
        return membros.stream()
                .filter(sessionId -> !sessionId.equals(sessionIdRemetente))
                .map(uuidPorSessao::get)
                .filter(uuid -> uuid != null)
                .collect(Collectors.toSet());
    }

    public boolean salaExiste(String chave) {
        return salas.containsKey(chave) && !salas.get(chave).isEmpty();
    }
}