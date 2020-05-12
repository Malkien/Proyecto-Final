package com.project.classes;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.LogManager;

/**
 * This class contain the methods and variables for the use of ibm watson assistant
 * @author Malkien
 */
public class IbmAssistant {
    private String name;
    private String url;
    private String apiKey;
    private Assistant assistant;
    private String assistantId;
    private String sessionId;
    private Boolean sessionOpen;

    public IbmAssistant(String name, String apiKey, String url){
        this.name = name;
        this.apiKey = apiKey;
        this.url = url;
        IamAuthenticator authenticator = new IamAuthenticator(apiKey); //sustituir por la apikey
        assistant = new Assistant(String.valueOf(LocalDate.now()), authenticator);
        assistant.setServiceUrl(url);
        assistantId = url; // sustituir por el ID de asistente
        sessionOpen = false;
    }
    public void createSession(){
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantId).build();
        SessionResponse session = assistant.createSession(createSessionOptions).execute().getResult();
        sessionId = session.getSessionId();
        sessionOpen = true;
    }
    public void deleteSession(){
        DeleteSessionOptions deleteSessionOptions = new DeleteSessionOptions.Builder(assistantId, sessionId).build();
        assistant.deleteSession(deleteSessionOptions).execute();
        sessionOpen = false;
    }

    /**
     * METHOD THAN CALL THE IBM ASSISTANT AND SEND A STRING, LATER GET THE ANSWER IF HAVE ONE
     * WARNING CREATE A SESSION BEFORE IT USE AND DELETE THE SESSION AFTER
     * @param text THE STRING
     * @return THE ANSWER
     */
    public String chatUp(String text){
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(text)
                .build();
        // Enviar mensaje al asistente.
        MessageOptions messageOptions = new MessageOptions.Builder(assistantId, sessionId)
                .input(input)
                .build();
        MessageResponse response = assistant.message(messageOptions).execute().getResult();

        // Si se detecta una intención, imprimirla en la consola.
        List<RuntimeIntent> responseIntents = response.getOutput().getIntents();
        if(responseIntents.size() > 0) {
            System.out.println("Detected intent: #" + responseIntents.get(0).intent());
        }

        // Imprimir la salida del diálogo, si la hay. Se supone que solo hay una respuesta de texto.
        List<RuntimeResponseGeneric> responseGeneric = response.getOutput().getGeneric();
        if(responseGeneric.size() > 0) {
            if(responseGeneric.get(0).responseType().equals("text")) {
                return responseGeneric.get(0).text();
            }
        }
        return null;
    }

    public Boolean isSessionOpen() {
        return sessionOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
