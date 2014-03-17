package com.example.testaudio;

import java.io.IOException;
import java.io.OutputStream;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * ��Ƶ�ɼ���
 * @author totoro
 *
 */
public class Recorder {
	private static final String TAG = "Totoro:Recorder";
	// ��Ƶ��ȡԴΪ�绰ͨ������
	private int audioSource = MediaRecorder.AudioSource.VOICE_CALL;
	// ������Ƶ�����ʣ�44100��Ŀǰ�ı�׼������ĳЩ�豸��Ȼ֧��22050��16000��11025
	public static int sampleRateInHz = 8000;// 44100;
	// ������Ƶ��¼�Ƶ�����CHANNEL_IN_STEREOΪ˫������CHANNEL_CONFIGURATION_MONOΪ������
	@SuppressWarnings("deprecation")
	public static int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_STEREO;// AudioFormat.CHANNEL_IN_STEREO;
	// ��Ƶ���ݸ�ʽ:PCM 16λÿ����������֤�豸֧�֡�PCM 8λÿ����������һ���ܵõ��豸֧�֡�
	public static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	// ��Ƶ��С
	private int bufSize;
	public boolean isRecording = false;
	public boolean isOpen = false;
	private static AudioRecord mRecord;
	
	public Recorder () {
		bufSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig,
				audioFormat);
		mRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig,
				audioFormat, bufSize);
	}
	
	/**
	 * ��ʼ¼��
	 * 
	 * @param out ��Ƶ�����
	 * @return boolean 
	 */
	public boolean startRecord(OutputStream out) {
		byte[] audioData = new byte[bufSize];
		while(isRecording) {
			int result = mRecord.read(audioData,0, bufSize);
			if (result > 0) {
				try {
					out.write(audioData, 0, result);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}		
		}
		return isRecording;
	}
	
	/**
	 * ��ʼ¼��
	 * 
	 * @return boolean 
	 */
	public boolean startRecord( ) {
		byte[] audioData = new byte[bufSize];
		while(isRecording) {
			int result = mRecord.read(audioData,0, bufSize);
			if (result > 0) {
				Log.v(TAG,new String(audioData));
//				try {
//					out.write(audioData, 0, result);
//				} catch (IOException e) {
//					e.printStackTrace();
//					return false;
//				}
			}		
		}
		return isRecording;
	}
	
	/**
	 * ��¼����
	 */
	public void openRecorder() {
		if (mRecord != null) {
			if (mRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
				Log.v(TAG, "������δ��ʼ���ɹ���");
			} else {
				mRecord.startRecording();
				isOpen = true;
				isRecording = true;
			}
		} else {
			Log.v(TAG, "������δ��ʼ����");
		}
	}
	
	/**
	 * ֹͣ¼��
	 */
	public void stopRecord() {
		if (mRecord != null) {
			mRecord.stop();
			isOpen = false;
		} else {
			Log.v(TAG,"��ͼ�ر�һ���յ�¼������");
		}
	}
	
	/**
	 * �ر�¼��
	 */
	public void closeRecorder() {
		if (mRecord != null) {
			mRecord.release();
			mRecord = null;
			isOpen = false;
		}
	}
}
