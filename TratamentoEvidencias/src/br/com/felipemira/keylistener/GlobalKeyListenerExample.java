package br.com.felipemira.keylistener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


import br.com.felipemira.ExecutarCasoDeTeste;

public class GlobalKeyListenerExample implements NativeKeyListener {
	
    public void nativeKeyPressed(NativeKeyEvent e) {
    	final JDialog dialog = new JDialog();
    	dialog.setAlwaysOnTop(true); 
    	
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        
        if ((e.getKeyCode() == NativeKeyEvent.VC_UP) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) && ((e.getModifiers() & NativeKeyEvent.SHIFT_MASK) !=0)) {
            System.out.println("Passou!");
            ExecutarCasoDeTeste.word.inserirEvidenciaManual(true, true, ExecutarCasoDeTeste.passo);
        }
        
        if ((e.getKeyCode() == NativeKeyEvent.VC_DOWN) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) && ((e.getModifiers() & NativeKeyEvent.SHIFT_MASK) !=0)) {
            System.out.println("Falhou!");
            ExecutarCasoDeTeste.word.inserirEvidenciaManual(false, true, ExecutarCasoDeTeste.passo);
            ExecutarCasoDeTeste.word.finalizarEvidencia();
        	ExecutarCasoDeTeste.sair = true;
        }
        
        if ((e.getKeyCode() == NativeKeyEvent.VC_LEFT) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) && ((e.getModifiers() & NativeKeyEvent.SHIFT_MASK) !=0)) {
            System.out.println("Volta um Passo!");
            if(ExecutarCasoDeTeste.passo > 1){
            	ExecutarCasoDeTeste.passo --;
            }else if (ExecutarCasoDeTeste.passo == 1){
            	   
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
            }else{
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
            	JOptionPane.showMessageDialog(dialog, "Passo: " + ExecutarCasoDeTeste.passo);
            }
        }
        
        if ((e.getKeyCode() == NativeKeyEvent.VC_RIGHT) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0) && ((e.getModifiers() & NativeKeyEvent.SHIFT_MASK) !=0)) {
            System.out.println("Avança um Passo!");
            ExecutarCasoDeTeste.passo ++;
            if(ExecutarCasoDeTeste.passo > ExecutarCasoDeTeste.numeroDePassos - 1){
            	ExecutarCasoDeTeste.word.finalizarEvidencia();
            	ExecutarCasoDeTeste.sair = true;
            }else{
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
            	JOptionPane.showMessageDialog(dialog, "Passo: " + ExecutarCasoDeTeste.passo);
            }
            
        }
        
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
        }
        
        try {
			Thread.sleep(10);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

	public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
	
}
