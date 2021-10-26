// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.intellij.sdk.kotlin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import lib.NewFileClass
import java.io.File
import java.io.IOException
import java.nio.file.Files
import javax.swing.JOptionPane

class GenerateAction : DumbAwareAction() {

  override fun actionPerformed(event: AnActionEvent) {
    val target: String = JOptionPane.showInputDialog("Type here the absolute target path:");
    val file: File = File(target);

    val current = Thread.currentThread().contextClassLoader
    try {
      Thread.currentThread().contextClassLoader = this.javaClass.classLoader


//FUNKČNÍ - ale k ničemu
//
//      try {
//        Files.createFile(file.toPath().resolve("newFile.txt"));
//          val s: String = Files.readString(file.toPath().resolve("newFile.txt"));
//        println(file.toPath().resolve("newFile.txt"));
//      }catch (e: FileAlreadyExistsException){
//        throw RuntimeException("File " + target + " already exists.", e);
//      }catch (e: IOException){
//        throw RuntimeException(String.format("Cannot make a new file to: '%s'", target), e);
//      }

      

//NEFUNKČNÍ - chyba:
//Caused by: java.lang.ClassNotFoundException: lib.NewFileClass PluginClassLoader[PluginDescriptor(name=SDK: Kotlin Demo, id=org.intellij.sdk.kotlin, path=D:\sramk\Documents\vysoka_skola\bakalarka\git_download_others\intellij-sdk-code-samples-main\intellij-sdk-code-samples-main\kotlin_demo\build\idea-sandbox\plugins\kotlin, version=2.0.0)] com.intellij.ide.plugins.cl.PluginClassLoader@4b3a9019

    try {
        NewFileClass.makeNewFile(target);
      }catch (e: IOException) {
        throw RuntimeException(String.format("Cannot make a new file to: '%s'", target));
      }


    } finally {
      Thread.currentThread().contextClassLoader = current
    }
  }
}
