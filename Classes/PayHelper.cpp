#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "PayHelper.h"
#include "cocos2d.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include <android/log.h>


#define COCOS2D_DEBUG 1

#define TAG "Gmw"  
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)  

const char* PayClassName = "org/cocos2dx/cpp/AppActivity";

void PayHelper::onPay()
{
	cocos2d::JniMethodInfo t;
	bool ret = cocos2d::JniHelper::getStaticMethodInfo(t,PayClassName,"getObj","()Ljava/lang/Object;");
    jobject jobj;

    if(ret)


    {

        jobj = t.env->CallStaticObjectMethod(t.classID,t.methodID);

    }

	if (cocos2d::JniHelper::getMethodInfo(t, PayClassName, "onPay", "()V"))
	{

		t.env->CallVoidMethod(jobj, t.methodID);
		cocos2d::CCLog("Jni onPay()");

	}
}

#else

void PayHelper::onPay()
{
    cocos2d::CCLog("PayHelper::onPay()");
	return; //nothing
}

#endif
