package com.example.pluginkotlin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.IOException

class ModifyFileAction {

    fun findFileInProject(project: Project, relativePath: String): VirtualFile? {
        val projectBasePath = project.basePath ?: return null

        // Récupère le fichier en tant que VirtualFile
        return LocalFileSystem.getInstance().findFileByPath(relativePath)
    }

    fun modifyFileContent(event: AnActionEvent, file: VirtualFile, contentToAdd: String) {
        ApplicationManager.getApplication().invokeLater {
            WriteCommandAction.runWriteCommandAction(event.project) {
                try {
                    // Lire le contenu existant
                    val existingContent = VfsUtil.loadText(file)

                    // Ajouter du contenu et écrire dans le fichier
                    val newContent = "$existingContent\n$contentToAdd"
                    file.setBinaryContent(newContent.toByteArray())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun addLineToFileAtIndex(event: AnActionEvent, file: VirtualFile, line: String, index: Int) {
        ApplicationManager.getApplication().invokeLater {
            WriteCommandAction.runWriteCommandAction(event.project) {
                try {
                    // Lire le contenu actuel du fichier
                    val existingContent = file.inputStream.bufferedReader().use { it.readText() }

                    // Diviser le contenu en lignes
                    val lines = existingContent.lines().toMutableList()

                    // Insérer la nouvelle ligne à l'index donné
                    if (index in 0..lines.size) {
                        lines.add(index, line)
                    } else {
                        throw IllegalArgumentException("Index $index hors des limites du fichier (0-${lines.size}).")
                    }

                    // Écrire le contenu modifié dans le fichier
                    val newContent = lines.joinToString("\n")
                    file.setBinaryContent(newContent.toByteArray())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun removeLineFromFileAtIndex(event: AnActionEvent, file: VirtualFile, index: Int) {
        ApplicationManager.getApplication().invokeLater {
            WriteCommandAction.runWriteCommandAction(event.project) {
                try {
                    // Lire le contenu actuel du fichier
                    val existingContent = file.inputStream.bufferedReader().use { it.readText() }

                    // Diviser le contenu en lignes
                    val lines = existingContent.lines().toMutableList()

                    // Vérifier si l'index est valide
                    if (index in lines.indices) {
                        // Supprimer la ligne à l'index donné
                        lines.removeAt(index)
                    } else {
                        throw IllegalArgumentException("Index $index hors des limites du fichier (0-${lines.size - 1}).")
                    }

                    // Écrire le contenu modifié dans le fichier
                    val newContent = lines.joinToString("\n")
                    file.setBinaryContent(newContent.toByteArray())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
