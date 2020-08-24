package com.example.sznake.sensorServices;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;

public class FingerprintService {
    private FingerprintManager m_fingerprintManager;
    private CancellationSignal m_cancellationSignal;
    private FingerprintManager.AuthenticationCallback m_authenticationCallback;
    private OnAuthenticationListener m_authorisationListener;

    public interface OnAuthenticationListener {
        void onAuth();
    }

    public void setOnAuthenticationListener(OnAuthenticationListener listener){
        m_authorisationListener = listener;
    }

    public FingerprintService(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            m_fingerprintManager = context.getSystemService(FingerprintManager.class);
            m_cancellationSignal = new CancellationSignal();

            m_authenticationCallback = new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    // do not implement unless You know what You are doing
                }

                @Override
                public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                    m_authorisationListener.onAuth();
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    m_authorisationListener.onAuth();
                }

                @Override
                public void onAuthenticationFailed() {
                    m_authorisationListener.onAuth();
                }
            };
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startListening(){
        if (isFingerScannerAvailableAndSet() ) {
            try{
                if(m_cancellationSignal == null)
                    m_cancellationSignal = new CancellationSignal();
                m_fingerprintManager.authenticate(null, m_cancellationSignal, 0 /* flags */, m_authenticationCallback, null);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stopListening(){
        if ( isFingerScannerAvailableAndSet() ) {
            try {
                m_cancellationSignal.cancel();
                m_cancellationSignal = null;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isFingerScannerAvailableAndSet(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return false;
        if( m_fingerprintManager == null )
            return false;
        if( !m_fingerprintManager.isHardwareDetected() )
            return false;
        if( !m_fingerprintManager.hasEnrolledFingerprints())
            return false;

        return true;
    }
}