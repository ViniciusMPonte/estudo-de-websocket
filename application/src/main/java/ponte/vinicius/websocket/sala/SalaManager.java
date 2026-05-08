package ponte.vinicius.websocket.sala;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SalaManager {

    private final Map<String, Set<String>> salas = new ConcurrentHashMap<>();
    private final Map<String, String> sessaoPorSala = new ConcurrentHashMap<>();
    private final Map<String, String> apelidoPorSessao = new ConcurrentHashMap<>();

    public void entrar(String chave, String sessionId, String apelido) {
        salas.computeIfAbsent(chave, k -> ConcurrentHashMap.newKeySet()).add(sessionId);
        sessaoPorSala.put(sessionId, chave);
        apelidoPorSessao.put(sessionId, apelido);
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
        apelidoPorSessao.remove(sessionId);
    }

    public String getSalaDoCliente(String sessionId) {
        return sessaoPorSala.get(sessionId);
    }

    public String getApelido(String sessionId) {
        return apelidoPorSessao.getOrDefault(sessionId, sessionId);
    }

    public boolean salaExiste(String chave) {
        return salas.containsKey(chave) && !salas.get(chave).isEmpty();
    }
}