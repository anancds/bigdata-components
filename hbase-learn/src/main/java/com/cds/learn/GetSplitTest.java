package com.cds.learn;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hbase.util.Bytes;

public class GetSplitTest {

  public static final byte ZERO_BYTE_VALUE = (byte) 48;
  public static final byte NINE_BYTE_VALUE = (byte) 57;
  public static final byte A_BYTE_VALUE = (byte) 65;
  public static final byte Z_BYTE_VALUE = (byte) 122;
  public static int res = 0;
  public static List<Integer> byteSizes = new ArrayList<>();

  public static List<Byte> startA = new ArrayList<>();
  public static List<Byte> endA = new ArrayList<>();

  public static void addLastChar(int plateNum) {
    while (res < plateNum - 1) {
      startA.add(A_BYTE_VALUE);
      endA.add(Z_BYTE_VALUE);
      res += 56;
      byteSizes.add(56);
    }
  }

  public static void calByteSize(byte[] start, byte[] end, int plateNum) {
    for (int i = 0; i < start.length; i++) {
      int temp = end[i] - start[i] - 1;
      if (temp > 0 && res < (plateNum - 1)) {
        res += temp;
        byteSizes.add(temp);
      } else {
        byteSizes.add(0);
      }
      startA.add(start[i]);
      endA.add(end[i]);

    }
  }

  public static byte[] paddingChar(byte[] start, byte[] end, boolean isGreater) {
    if (isGreater) {
      byte[] tempRes = new byte[start.length];
      for (int i = 0; i < start.length; i++) {
        if (i < end.length) {
          tempRes[i] = end[i];
        } else {
          if (isAlphabet(start[i])) {
            tempRes[i] = Z_BYTE_VALUE;
          } else if (isNumeric(start[i])) {
            tempRes[i] = NINE_BYTE_VALUE;
          } else {
            tempRes[i] = start[i];
          }
        }
      }
      return tempRes;
    } else {
      byte[] tempRes = new byte[end.length];
      for (int i = 0; i < end.length; i++) {
        if (i < start.length) {
          tempRes[i] = start[i];
        } else {
          if (isAlphabet(end[i])) {
            tempRes[i] = A_BYTE_VALUE;
          } else if (isNumeric(end[i])) {
            tempRes[i] = ZERO_BYTE_VALUE;
          } else {
            tempRes[i] = end[i];
          }
        }
      }
      return tempRes;
    }
  }

  public static List<byte[]> getSplitKeyValues(byte[] start, byte[] end, int startSplitPoint,
      int splitStep, int plateNum) {
    List<byte[]> resultSplitKeys = new ArrayList<>();

    if (start.length == 0 && end.length == 0) {
      return resultSplitKeys;
    }
    // 拆分块数小于2 不拆分
    if (plateNum < 2) {
      return resultSplitKeys;
    }

//    List<Integer> byteSizes = new ArrayList<>();

//    List<Byte> startA = new ArrayList<>();
//    List<Byte> endA = new ArrayList<>();
    if (start.length == end.length) {
      calByteSize(start, end, plateNum);

      addLastChar(plateNum);

    } else if (start.length > end.length) {
      byte[] tempRes = paddingChar(start, end, true);

      calByteSize(start, tempRes, plateNum);

      addLastChar(plateNum);
    } else if (start.length < end.length) {
      byte[] tempRes = paddingChar(start, end, false);
      calByteSize(tempRes, end, plateNum);
      addLastChar(plateNum);

    }

    byte[] aa = new byte[startA.size()];
    for (int i = 0; i < startA.size(); i++) {
      aa[i] = startA.get(i);
    }
    for (int i = 0; i < byteSizes.size(); i++) {
      int len = byteSizes.get(i);
      for (int j = 0; j < len; j++) {
        byte[] temp = new byte[aa.length];
        for (int k = 0; k < aa.length; k++) {
          if (i == k) {
            temp[k] = (byte) (aa[k] + 1);
            byteSizes.set(k, byteSizes.get(k) - 1);
          } else {
            temp[k] = startA.get(k);
          }
        }
        aa = temp;
        if (resultSplitKeys.size() >= (plateNum - 1)) {
          break;
        }
        resultSplitKeys.add(temp);
      }
    }

    for (byte[] a : resultSplitKeys) {
      System.out.println(Bytes.toString(a));
    }

//    if (start.length == end.length) {
//
//      for (int i = 0; i < start.length; i++) {
//        byteSizes.add(end[i] - start[i] - 1);
//      }
//
//      for (int i = 0; i < byteSizes.size(); i++) {
//        int len = byteSizes.get(i);
//        for (int j = 0; j < len; j++) {
//          byte[] temp = new byte[byteSizes.size()];
//          for (int k = 0; k < byteSizes.size(); k++) {
//            if (i == k) {
//              if (!isValidCharacter(start[k])) {
//                temp[k] = start[k];
//              } else {
//                temp[k] = (byte) (start[k] + 1);
//                byteSizes.set(k, byteSizes.get(k) - 1);
//              }
//            } else {
//              temp[k] = start[k];
//            }
//          }
//          start = temp;
//          resultSplitKeys.add(temp);
//        }
//      }
//
//      for (byte[] a : resultSplitKeys) {
//        System.out.println(Bytes.toString(a));
//      }
//
//    } else if (start.length > end.length) {
//      byte[] tempRes = new byte[start.length];
//      for (int i = 0; i < start.length; i++) {
//        if (i < end.length) {
//          tempRes[i] = end[i];
//        } else {
//          if (isAlphabet(start[i])) {
//            tempRes[i] = Z_BYTE_VALUE;
//          } else if (isNumeric(start[i])) {
//            tempRes[i] = NINE_BYTE_VALUE;
//          } else {
//            tempRes[i] = start[i];
//          }
//        }
//      }
//
//      for (int i = 0; i < start.length; i++) {
//        byteSizes.add(tempRes[i] - start[i] - 1);
//      }
//
//      for (int i = 0; i < byteSizes.size(); i++) {
//        int len = byteSizes.get(i);
//        for (int j = 0; j < len; j++) {
//          byte[] temp = new byte[byteSizes.size()];
//          for (int k = 0; k < byteSizes.size(); k++) {
//            if (i == k) {
//              temp[k] = (byte) (start[k] + 1);
//              byteSizes.set(k, byteSizes.get(k) - 1);
//            } else {
//              temp[k] = start[k];
//            }
//          }
//          start = temp;
//          resultSplitKeys.add(temp);
//        }
//      }
//      for (byte[] a : resultSplitKeys) {
//        System.out.println(Bytes.toString(a));
//      }
//    } else if (start.length < end.length) {
//      byte[] tempRes = new byte[end.length];
//      for (int i = 0; i < end.length; i++) {
//        if (i < start.length) {
//          tempRes[i] = start[i];
//        } else {
//          if (isAlphabet(end[i])) {
//            tempRes[i] = A_BYTE_VALUE;
//          } else if (isNumeric(end[i])) {
//            tempRes[i] = ZERO_BYTE_VALUE;
//          } else {
//            tempRes[i] = end[i];
//          }
//        }
//      }
//
//      for (int i = 0; i < end.length; i++) {
//        byteSizes.add(end[i] - tempRes[i] - 1);
//      }
//
//      for (int i = 0; i < byteSizes.size(); i++) {
//        int len = byteSizes.get(i);
//        for (int j = 0; j < len; j++) {
//          byte[] temp = new byte[byteSizes.size()];
//          for (int k = 0; k < byteSizes.size(); k++) {
//            if (i == k) {
//              temp[k] = (byte) (tempRes[k] + 1);
//              byteSizes.set(k, byteSizes.get(k) - 1);
//            } else {
//              temp[k] = tempRes[k];
//            }
//          }
//          tempRes = temp;
//          resultSplitKeys.add(temp);
//        }
//      }
//
//      for (byte[] a : resultSplitKeys) {
//        System.out.println(Bytes.toString(a));
//      }
//    }

    // 48 为字符串0
    byte[] startCalValue = cutBytes(start, startSplitPoint, splitStep, ZERO_BYTE_VALUE);
    // 57 为字符串9
    byte[] endCalValue = cutBytes(end, startSplitPoint, splitStep, NINE_BYTE_VALUE);

    int startInt = Integer.parseInt(Bytes.toString(startCalValue));
    int endInt = Integer.parseInt(Bytes.toString(endCalValue));
    int totalStep = endInt - startInt;
    // 总共步长小于1 无法拆分
    if (totalStep < 1) {
      return resultSplitKeys;
    }

    int keyStep = totalStep / plateNum;
    // 余数值
    int keyRemainder = totalStep % plateNum;
    // keyStep 为0的情况下
    if (keyStep == 0 && totalStep > 0) {
      plateNum = totalStep;
      keyStep = 1;
      keyRemainder = 0;
    }

    byte[] souceByte = start;
    if (start.length == 0) {
      souceByte = end;
    }

    // 余数除不尽的情况下,
    // 余数分配方式 前面分配1 直到余数分配完成
    for (int i = 1; i < plateNum; i++) {
      int remainder = i;
      if (keyRemainder < i) {
        remainder = keyRemainder;
      }

      int splitKey = startInt + keyStep * i + remainder;
      if (endInt > splitKey) {
        resultSplitKeys.add(buildKeyByRule(splitKey, souceByte, startSplitPoint, splitStep));
      }
    }

    return resultSplitKeys;
  }

  private static boolean isNumeric(byte a) {
    boolean res = false;
    if (a >= 48 && a <= 57) {
      res = true;
    }
    return res;
  }

  private static boolean isAlphabet(byte a) {
    boolean res = false;
    if ((a >= 65 && a <= 90) || (a >= 97 && a <= 122)) {
      res = true;
    }
    return res;
  }

  private static boolean isValidCharacter(byte a) {
    boolean res = false;
    if (isNumeric(a) || isAlphabet(a)) {
      res = true;
    }
    return res;
  }

  private static byte[] buildKeyByRule(int keyValue, byte[] souceByte, int startSplitPoint,
      int splitStep) {
    // 转换字符串
    String valueStr = Integer.valueOf(keyValue).toString();
    // 判断转换字符串长度是否与位数一致, 否则前面补零
    if (splitStep != valueStr.length()) {
      int size = splitStep - valueStr.length();
      for (int i = 0; i < size; i++) {
        valueStr = "0" + valueStr;
      }
    }
    byte[] valueByte = Bytes.toBytes(valueStr);

    // 根据规则构建后返回
    return replaceBytes(souceByte, valueByte, startSplitPoint, splitStep, (byte) 0);
  }

  private static byte[] replaceBytes(byte[] souceByte, byte[] replaceByte, int startSplitPoint,
      int splitStep,
      byte defaultValue) {
    int length = souceByte.length;
    int totalLength = souceByte.length;
    if ((startSplitPoint + splitStep) > length) {
      totalLength = startSplitPoint + splitStep;
    }
    byte[] resultByte = new byte[totalLength];
    for (int i = 0; i < totalLength; i++) {
      if (i >= startSplitPoint && i < (startSplitPoint + splitStep)) {
        resultByte[i] = replaceByte[i - startSplitPoint];
      } else {
        if (i > length) {
          resultByte[i] = defaultValue;
        } else {
          resultByte[i] = souceByte[i];
        }
      }
    }
    return resultByte;
  }

  private static byte[] cutBytes(byte[] souceByte, int startSplitPoint, int splitStep,
      byte defaultValue) {
    byte[] resultByte = new byte[splitStep];
    int length = souceByte.length;
    for (int i = 0; i < splitStep; i++) {
      if ((startSplitPoint + i) >= length || souceByte[startSplitPoint + i] < ZERO_BYTE_VALUE
          || souceByte[startSplitPoint + i] > NINE_BYTE_VALUE) {
        resultByte[i] = defaultValue;
      } else {
        resultByte[i] = souceByte[startSplitPoint + i];
      }
    }
    return resultByte;
  }

  private static int calculateSplitPlateTimes(long regionNum, long splitThreshold) {
    int plateNum = new Double(regionNum / splitThreshold).intValue();
    if (regionNum % splitThreshold != 0) {
      plateNum++;
    }
    return plateNum;
  }

  public static void main(String[] args) {

    System.out.println(calculateSplitPlateTimes(10, 11));
//    byte[] a = Bytes.toBytes("1-bcd");
//    byte[] a = Bytes.toBytes("1-bcd");
    byte[] a = Bytes.toBytes("aaa");
//    byte[] a = Bytes.toBytes("ab");
    byte[] c = new byte[5];
    for (int i = 0; i < a.length; i++) {
      c[i] = (byte) (a[i] + 1);
    }
//    byte[] b = Bytes.toBytes("1-efg");
//    byte[] b = Bytes.toBytes("2");
    byte[] b = Bytes.toBytes("b");
//    byte[] b = Bytes.toBytes("cd");
    String s = Bytes.toString(a);
    int p = 10;
    List<byte[]> bytes = getSplitKeyValues(a, b, 0, 3, p);


  }

}
