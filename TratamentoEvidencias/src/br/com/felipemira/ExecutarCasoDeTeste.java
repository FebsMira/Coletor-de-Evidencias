package br.com.felipemira;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import br.com.felipemira.arquivos.office.WordAutomacao;
import br.com.felipemira.keylistener.GlobalKeyListenerExample;


public class ExecutarCasoDeTeste {
	
	public static WordAutomacao word;
	public static int passo = 1;
	public static int numeroDePassos;
	public static boolean sair = false;
	public static boolean breake = false;
	
	public static void executar(String executor, String ciclo, String ambiente, String caminhoEvidencia){
		
		String realPath = caminhoEvidencia;
		String myPath = realPath.substring(0, realPath.lastIndexOf("/") + 1);
		
		ExecutarCasoDeTeste.word = new WordAutomacao(caminhoEvidencia, myPath + "evidencia.png");
		
		ExecutarCasoDeTeste.numeroDePassos = ExecutarCasoDeTeste.word.getNumTables();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String dataExecucao = dateFormat.format(cal.getTime());
		
		ExecutarCasoDeTeste.word.inserirDadoTabela(0, 1, 1, ambiente, false);
		ExecutarCasoDeTeste.word.inserirDadoTabela(0, 1, 3, dataExecucao, false);
		ExecutarCasoDeTeste.word.inserirDadoTabela(0, 6, 1, executor, false);
		ExecutarCasoDeTeste.word.inserirDadoTabela(0, 6, 3, ciclo, false);
		
		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
		
		try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
		GlobalKeyListenerExample keyListener = new GlobalKeyListenerExample();
		GlobalScreen.addNativeKeyListener(keyListener);
		
		final JDialog dialog = new JDialog();
    	dialog.setAlwaysOnTop(true); 
		
		//JOptionPane.showMessageDialog(null, "Está no primeiro passo");
		
    	Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }               
                dialog.dispose();
            }
        });
        t1.start();
        JOptionPane.showMessageDialog(dialog, "Está no primeiro passo!");
		
		
		while(!(sair) && !(breake)){
			System.out.println(sair);
			System.out.println(breake);
		}
		
		System.out.println("Evidência Finalizada");
		
		GlobalScreen.removeNativeKeyListener(keyListener);
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			System.err.println("There was a problem unregistering the native hook.");
            System.err.println(e.getMessage());

            System.exit(1);
			e.printStackTrace();
		}
		
		ExecutarCasoDeTeste.word = null;
		
	}
	
}
