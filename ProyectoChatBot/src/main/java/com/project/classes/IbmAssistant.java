package com.project.classes;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.LogManager;

public class IbmAssistant {
    private String url;
    private String apiKey;
    private Assistant assistant;
    private String assistantId;
    private String sessionId;

    public IbmAssistant(String apiKey, String url){
        LogManager.getLogManager().reset();//Crear log
        IamAuthenticator authenticator = new IamAuthenticator("YJmV5K8mOWHPTUmTWTwO_U0v9PhPVvPlcfexgD2N6E1D"); //sustituir por la apikey
        assistant = new Assistant(String.valueOf(LocalDate.now()), authenticator);
        assistantId = "https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/34a99098-ec05-430b-92cd-43b5508e229a"; // sustituir por el ID de asistente
    }
    public void createSession(){
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder(assistantId).build();
        SessionResponse session = assistant.createSession(createSessionOptions).execute().getResult();
        sessionId = session.getSessionId();
    }
    public void deleteSession(){

    }

    public void chatUp(){
        createSession();
        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text("")
                .build();

        // Bucle de entrada/salida principal
        do {
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
                    System.out.println(responseGeneric.get(0).text());
                }
            }

            // Solicitar la siguiente ronda de información de entrada.
            System.out.print(">> ");
            String inputText = System.console().readLine();
            input = new MessageInput.Builder()
                    .messageType("text")
                    .text(inputText)
                    .build();
        } while(!input.text().equals("quit"));
    }
}
