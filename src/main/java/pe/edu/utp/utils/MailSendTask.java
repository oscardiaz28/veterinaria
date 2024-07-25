package pe.edu.utp.utils;

import pe.edu.utp.util.MailSender;

public class MailSendTask implements Runnable{
    private String message;
    private String destinatario;
    public MailSendTask(String message, String destinatario){
        this.message = message;
        this.destinatario = destinatario;
    }
    @Override
    public void run() {
        System.out.println("El mensaje se ha enviado...");
        MailSender.sendMail(this.message, this.destinatario);
        System.out.println("El mensaje ha sido recibido");
    }

}
