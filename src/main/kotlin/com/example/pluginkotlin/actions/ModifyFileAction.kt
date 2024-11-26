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

class ModifyFileAction{

    fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return

        // Exemple : Chemin relatif d'un fichier dans le projet
        val relativePath = "src/main/resources/example.txt"

        // Récupérer le fichier à partir du système de fichiers local
        val file = findFileInProject(project, relativePath)
        if (file != null) {
            modifyFileContent(event, file, "Nouvelle ligne ajoutée !")
        } else {
            println("Fichier introuvable : $relativePath")
        }
    }

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
}
