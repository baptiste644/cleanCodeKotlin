package com.example.pluginkotlin.actions

import com.example.pluginkotlin.tasks.DialogInterfaceTask
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ShowPopupAction : AnAction()  {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = DialogInterfaceTask()
        if (dialog.showAndGet()) {
            when (dialog.selectedOption) {
                0 -> executeAction1(e)
                1 -> executeAction2()
                2 -> executeAction3()
                else -> throw IllegalStateException("Option non valide : ${dialog.selectedOption}")
            }
        }
    }

    private fun executeAction1(event : AnActionEvent) {
        println("Action 1 exécutée")
        val project = event.project ?: return
        val relativePath = GetCurrentFileDirectoryAction().actionPerformed(event) + "/test.txt"
        val file = ModifyFileAction().findFileInProject(project, relativePath)
        if (file != null) {
            ModifyFileAction().modifyFileContent(event, file, "Nouvelle ligne ajoutée !")
        } else {
            println("Fichier introuvable : $relativePath")
        }
            }

    private fun executeAction2() {
        println("Action 2 exécutée")
    }

    private fun executeAction3() {
        println("Action 3 exécutée")
    }


}