package com.example.testaudio;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * ��������
 * @author totoro
 *
 */
public class Player {
	private static final String TAG = "Totoro:Player";
	// ��Ƶ����
	private int streamType = AudioManager.STREAM_MUSIC;
	// ��Ƶ��С
	private int bufSize;
	// ��Ƶģʽ
	private int mode = AudioTrack.MODE_STREAM;
	public boolean isOpen = false;
	public boolean isPlaying = false;
	private static AudioTrack mTrack;
	
	public Player() {
		bufSize = AudioTrack.getMinBufferSize(Recorder.sampleRateInHz, Recorder.channelConfig,
				Recorder.audioFormat);
		mTrack = new AudioTrack(streamType, Recorder.sampleRateInHz, Recorder.channelConfig,
				Recorder.audioFormat, bufSize, mode);
	}
	
	/**
	 * �򿪲�����
	 */
	public void openPlayer() {
		if (mTrack != null) {
			mTrack.play();
			isOpen = true;
		} else {
			Log.v(TAG, "������δ��ʼ����");
		}
	}
	
	/**
	 * ��ʼ����
	 * @param data ����������Դ
	 */
	public void startPlay(byte[] data) {
		if (mTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
			isPlaying = true;
			mTrack.write(data, 0, data.length);
		}
	}
	
	/**
	 * ֹͣ����
	 */
	public void stopPlayer() {
		if (mTrack != null) {
			if (mTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
				mTrack.stop();
				isPlaying = false;
			}
		} else {
			Log.v(TAG,"��ͼ�ر�һ���յĲ�������");
		}
	}
	
	/**
	 * �رղ�����
	 */
	public void closePlayer() {
		if (mTrack != null) {
			mTrack.release();
			mTrack = null;
			isOpen = false;
		}
	}
}
