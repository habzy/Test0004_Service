/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/HelloJni/HelloJni.java
 */
jstring
Java_com_habzy_servicetest_process2_LocalService_checkJNI( JNIEnv* env,
                                                  jobject thiz )
{

	char s[] ={'J','N','I',' ','I','S','R','E','A','D','Y','!',0};

    return (*env)->NewStringUTF(env, s);
}

void
Java_com_habzy_servicetest_process2_LocalService_nativeCrash( JNIEnv* env,
                                                  jobject thiz )
{

	*((char*)0) = '1';

}
