#include <jni.h>
#include <string>
#include "libyuv.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_github_blizz2inght_mylibyuv_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"

JNIEXPORT jbyteArray JNICALL
Java_com_github_blizz2inght_mylibyuv_YuvUtils_ARGB2ABGR(JNIEnv *env, jclass clazz,
                                                        jbyteArray array, jint width, jint height) {
    jsize size = env->GetArrayLength(array);
    uint8_t *src = (uint8_t*)env->GetByteArrayElements(array, 0);
    uint8_t *dst = static_cast<uint8_t *>(malloc(sizeof(uint8_t) * size));
    libyuv::ABGRToARGB(src, width * 4, dst, width * 4, width, height);
    env->ReleaseByteArrayElements(array, reinterpret_cast<jbyte *>(src), 0);
    jbyteArray out =env->NewByteArray(size);
    env->SetByteArrayRegion(out, 0, size, reinterpret_cast<const jbyte *>(dst));
    return out;
//    return reinterpret_cast<jbyteArray>(dst);
}