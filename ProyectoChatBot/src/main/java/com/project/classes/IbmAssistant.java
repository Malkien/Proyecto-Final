package com.project.classes;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class contain the methods and variables for the use of ibm watson assistant
 * @author Malkien
 */
public class IbmAssistant {
    /**
     * The name
     */
    private String name;
    /**
     * The url
     */
    private String url;
    /**
     * The api Key
     */
    private String apiKey;
    /**
     * The assistant
     */
    private Assistant assistant;
    /**
     * The id of the assistant
     */
    private String assistantId;
    /**
     * The id of the session
     */
    private String sessionId;
    /**
     * If the sesion is open
     */
    private Boolean sessionOpen = false;

    /**
     * CONSTRUCTOR OF THE CLASS
     * @param name the name
     * @param apiKey the apikey
     * @param url the url
     * @param assistantId the assistant id
     */
    public IbmAssistant(String name, String apiKey, String url, String assistantId){
        this.name = name;
        this.apiKey = apiKey;
        this.url = url;
        // Suprimir mensajes de registro en stdout.
        //LogManager.getLogManager().reset();
        IamAuthenticator authenticator = new IamAuthenticator(apiKey); //sustituir por la apikey
        assistant = new Assistant(String.valueOf(LocalDate.now()), authenticator);
        assistant.setServiceUrl(url);
        this.assistantId = assistantId; // sustituir por el ID de asistente
    }

    /**
     * METHOD THAT CREATES THE SESSION
     */
    public void createSession(){
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantId).build();
        SessionResponse session = assistant.createSession(createSessionOptions).execute().getResult();
        sessionId = session.getSessionId();
        sessionOpen = true;
    }

    /**
     * METHOD THAT CLOSED THE SESSION
     */
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
        String currentAction = null;
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
        // Comprobar si hay alguna acción solicitada por el asistente.
        List<DialogNodeAction> responseActions = response.getOutput().getActions();
        if(responseActions != null) {
            if(responseActions.get(0).getType().equals("client")) {
                currentAction = responseActions.get(0).getName();
            }
        }
        // El usuario ha preguntado qué hora es, así que devolvemos como salida la hora del sistema local.
        if(currentAction.equals("display_time")) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("h:mm:ss a");
            LocalTime time = LocalTime.now();
            return "The current time is " + time.format(fmt) + ".";
        }
        return null;
    }

    /**
     * GETTER sessionOpen
     * @return the sessionOpen
     */
    public Boolean isSessionOpen() {
        return sessionOpen;
    }

    /**
     * GETTER name
     * @return the name
     */
    public String getName() {
        return name;
    }
}
