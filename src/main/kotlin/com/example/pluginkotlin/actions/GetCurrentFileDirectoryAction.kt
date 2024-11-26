package com.example.pluginkotlin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFile


class GetCurrentFileDirectoryAction{

    fun actionPerformed(event: AnActionEvent): String {
        // Récupérer le projet actuel
        val project = event.project ?: return ""

        // Récupérer le fichier actuellement ouvert dans l'éditeur
        val currentFile: VirtualFile? = FileEditorManager.getInstance(project).selectedFiles.firstOrNull()

        if (currentFile != null) {
            // Obtenir le dossier parent du fichier
            val parentDirectory = currentFile.parent
            if (parentDirectory != null) {
                println("Dossier du fichier ouvert : ${parentDirectory.path}")
                return parentDirectory.path
            } else {
                println("Le fichier n'a pas de dossier parent.")
            }
        } else {
            println("Aucun fichier n'est actuellement ouvert.")
        }
        return ""
    }
}