#include <jni.h>
#include <string>
#include "libyuv/include/libyuv/convert_argb.h"
#include <jni.h>

extern "C" jstring
Java_com_nothing_yuvutils_YuvUtils_stringFromJNI(
        JNIEnv *env,
        jclass clazz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" jbyteArray
Java_com_nothing_yuvutils_YuvUtils_ARGB2ABGR(JNIEnv *env, jclass clazz, jbyteArray array,
                                             jint width, jint height) {
    jsize size = env->GetArrayLength(array);
    auto *src = (uint8_t*)env->GetByteArrayElements(array, nullptr);
    auto *dst = static_cast<uint8_t *>(malloc(sizeof(uint8_t) * size));
    libyuv::ABGRToARGB(src, width * 4, dst, width * 4, width, height);
    env->ReleaseByteArrayElements(array, reinterpret_cast<jbyte *>(src), 0);
    jbyteArray out =env->NewByteArray(size);
    env->SetByteArrayRegion(out, 0, size, reinterpret_cast<const jbyte *>(dst));
    return out;
}
