package com.baofeng.mj.sdk.controller.daydream;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;
import java.util.concurrent.TimeUnit;

public class DaydreamController {
  private long F;
  
  private GattEventPacket gattEventPacket = new GattEventPacket();
  
  private int lastTimestamp;
  
  private void doMass(GattEventPacket paramGattEventPacket) {
      double d = Math.random() * -0.8D + 0.4D;
      paramGattEventPacket.orientationX = (float)(paramGattEventPacket.orientationX * d);
      paramGattEventPacket.orientationY = (float)(paramGattEventPacket.orientationY * d);
      paramGattEventPacket.orientationZ = (float)(paramGattEventPacket.orientationZ * d);
      paramGattEventPacket.orientationW = (float)Math.sqrt((1.0F - paramGattEventPacket.orientationX * paramGattEventPacket.orientationX - paramGattEventPacket.orientationY * paramGattEventPacket.orientationY - paramGattEventPacket.orientationZ * paramGattEventPacket.orientationZ));
      paramGattEventPacket.gyroX = (float)(paramGattEventPacket.gyroX * d);
      paramGattEventPacket.gyroY = (float)(paramGattEventPacket.gyroY * d);
      paramGattEventPacket.gyroZ = (float)(paramGattEventPacket.gyroZ * d);
      paramGattEventPacket.accelX = (float)(paramGattEventPacket.accelX * d);
      paramGattEventPacket.accelY = (float)(paramGattEventPacket.accelY * d);
      paramGattEventPacket.accelZ = (float)(paramGattEventPacket.accelZ * d);
  }
  
  public GattEventPacket bytesToEvent(BluetoothGattCharacteristic paramBluetoothGattCharacteristic) {
    if (!paramBluetoothGattCharacteristic.getUuid().toString().equals("00000001-1000-1000-8000-00805f9b34fb"))
      return null; 
    byte[] arrayOfByte = paramBluetoothGattCharacteristic.getValue();
    GattEventPacket gattEventPacket = this.gattEventPacket;
    if (arrayOfByte == null || arrayOfByte.length < 19)
      return null; 
    gattEventPacket.buffer = arrayOfByte;
    gattEventPacket.nPosition = 0;
    gattEventPacket.d = gattEventPacket.a(9, false);
    gattEventPacket.a(5, false);
    int i = gattEventPacket.a(13, true);
    int j = gattEventPacket.a(13, true);
    int k = gattEventPacket.a(13, true);
    int m = gattEventPacket.a(13, true);
    int n = gattEventPacket.a(13, true);
    int i1 = gattEventPacket.a(13, true);
    int i2 = gattEventPacket.a(13, true);
    int i3 = gattEventPacket.a(13, true);
    int i4 = gattEventPacket.a(13, true);
    int i5 = gattEventPacket.a(8, false);
    int i6 = gattEventPacket.a(8, false);
    int i7 = gattEventPacket.a(5, false);
    gattEventPacket.s[0] = GattEventPacket.angle2radian(i);
    gattEventPacket.s[1] = GattEventPacket.angle2radian(j);
    gattEventPacket.s[2] = GattEventPacket.angle2radian(k);
    float[] arrayOfFloat1 = gattEventPacket.s;
    float[] arrayOfFloat2 = gattEventPacket.t;
    float f1 = arrayOfFloat1[0];
    float f2 = arrayOfFloat1[1];
    float f3 = arrayOfFloat1[2];
    float f4 = f1 * f1 + f2 * f2 + f3 * f3;
    if (f4 > 0.0F) {
      boolean bool;
      float f = (float)Math.sqrt(f4);
      f4 = 0.5F * f;
      f = (float)Math.sin(f4) / f;
      arrayOfFloat2[0] = (float)Math.cos(f4);
      arrayOfFloat2[1] = f1 * f;
      arrayOfFloat2[2] = f2 * f;
      arrayOfFloat2[3] = f3 * f;
      gattEventPacket.orientationW = gattEventPacket.t[0];
      gattEventPacket.orientationX = gattEventPacket.t[1];
      gattEventPacket.orientationY = gattEventPacket.t[2];
      gattEventPacket.orientationZ = gattEventPacket.t[3];
      gattEventPacket.accelX = GattEventPacket.getGravity(m);
      gattEventPacket.accelY = GattEventPacket.getGravity(n);
      gattEventPacket.accelZ = GattEventPacket.getGravity(i1);
      gattEventPacket.gyroX = GattEventPacket.c(i2);
      gattEventPacket.gyroY = GattEventPacket.c(i3);
      gattEventPacket.gyroZ = GattEventPacket.c(i4);
      if (i5 != 0 || i6 != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      gattEventPacket.o = bool;
      gattEventPacket.touchpadX = i5 / 255.0F;
      gattEventPacket.touchpadY = i6 / 255.0F;
      boolean[] arrayOfBoolean = gattEventPacket.r;
      if ((i7 & 0x4) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayOfBoolean[3] = bool;
      arrayOfBoolean = gattEventPacket.r;
      if ((i7 & 0x1) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayOfBoolean[1] = bool;
      arrayOfBoolean = gattEventPacket.r;
      if ((i7 & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayOfBoolean[2] = bool;
      arrayOfBoolean = gattEventPacket.r;
      if ((i7 & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayOfBoolean[6] = bool;
      arrayOfBoolean = gattEventPacket.r;
      if ((i7 & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      arrayOfBoolean[5] = bool;
    } 
    n = this.gattEventPacket.d;
    if (n <= this.lastTimestamp)
      this.F = 512L + this.F; 
    this.lastTimestamp = n;
    gattEventPacket.timestamp = TimeUnit.MILLISECONDS.toNanos(this.F + n);
    doMass(gattEventPacket);
    return gattEventPacket;
  }
  
  public static class GattEventPacket {
    static final String TAG = GattEventPacket.class.getSimpleName();
    
    public float accelX;
    
    public float accelY;
    
    public float accelZ;
    
    public byte[] buffer;
    
    public int d;
    
    public float gyroX;
    
    public float gyroY;
    
    public float gyroZ;
    
    public int nPosition;
    
    public boolean o;
    
    public float orientationW;
    
    public float orientationX;
    
    public float orientationY;
    
    public float orientationZ;
    
    public boolean[] r = new boolean[7];
    
    float[] s = new float[3];
    
    float[] t = new float[4];
    
    public long timestamp;
    
    public float touchpadX;
    
    public float touchpadY;
    
    public static float angle2radian(int param1Int) {
      return (float)(6.283185307179586D * param1Int / 4095.0D);
    }
    
    static float c(int param1Int) {
      return (float)(6433.981754551896D * param1Int / 4095.0D / 180.0D);
    }
    
    static float getGravity(int param1Int) {
      return 78.4F * param1Int / 4095.0F;
    }
    
    public final int a(int param1Int, boolean param1Boolean) {
      int i = 0;
      if (param1Int <= 0 || param1Int > 16) {
        Log.i(TAG, "45Invalid number of bits to unpack: " + param1Int);
        return 0;
      } 
      int j = this.nPosition / 8;
      int k = this.nPosition + param1Int - (j << 3);
      int m = (k + 7) / 8;
      for (int n = j; n < j + m; n++)
        i = i << 8 | this.buffer[n] & 0xFF; 
      i = i >> (m << 3) - k & (1 << param1Int) - 1;
      this.nPosition += param1Int;
      return (!param1Boolean || (1 << param1Int & i) - 1 == 0 || i >> param1Int - 1 == 0) ? i : (-1 << param1Int | i);
    }
  }
}


/* Location:              C:\Users\WindowsTen\Desktop\com.baofeng.mj_dumped_3568.jar!\com\baofeng\mj\sdk\controller\daydream\DaydreamController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */