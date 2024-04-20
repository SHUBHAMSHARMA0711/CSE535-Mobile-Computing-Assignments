// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("assignment3question2");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("assignment3question2")
//      }
//    }

#include <jni.h>
#include <string>
#include <fcntl.h>
#include <sys/mman.h>
#include <android/bitmap.h>
#include <asm-generic/fcntl.h>
#include <android/NeuralNetworks.h>
#include <android/asset_manager_jni.h>

using namespace std;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_assignment3question2_MainActivity_helloFromCpp(JNIEnv *env, jobject /* this */) {
    string hello = "Hello from C++ Inside the CPP file";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_assignment3question2_MainActivity_scaleImage(JNIEnv *env, jobject instance, jobject bitmap) {

    void *ptr;
    AndroidBitmapInfo map;

    if (AndroidBitmap_getInfo(env, bitmap, &map) < 0) return NULL;

    if (map.format != ANDROID_BITMAP_FORMAT_RGBA_8888) return NULL;

    if (AndroidBitmap_lockPixels(env, bitmap, &ptr) < 0) return NULL;

    int *newBitmap = (int *) ptr;

    int newWidth = 224;
    int newHeight = 224;

    if (map.width > 224)
    {
        newWidth = map.width - 224;
    }

    if (map.width > 224)
    {
        newHeight = map.height - 224;
    }

    for (int i = 0; i < newWidth; ++i)
    {
        for (int j = 0; j < newHeight; ++j)
        {
            int invertedPixel = 0xFFFFFFFF - newBitmap[j * newWidth + i];
            newBitmap[j * newWidth + i] = invertedPixel;
        }
    }

    AndroidBitmap_unlockPixels(env, bitmap);

    return bitmap;
}
