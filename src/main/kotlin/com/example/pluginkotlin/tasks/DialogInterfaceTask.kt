package com.example.pluginkotlin.tasks

import com.intellij.openapi.ui.DialogWrapper
import javax.swing.*

class DialogInterfaceTask : DialogWrapper(true) {
    private var option1 = JRadioButton("Option 1")
    private var option2 = JRadioButton("Option 2")
    private var option3 = JRadioButton("Option 3")
    private val buttonGroup = ButtonGroup()

    var selectedOption: Int = -1
        private set

    init {
        init()
        title = "Sélectionner la tâche"
    }

    override fun createCenterPanel(): JComponent? {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)
        buttonGroup.add(option1)
        buttonGroup.add(option2)
        buttonGroup.add(option3)

        panel.add(option1)
        panel.add(option2)
        panel.add(option3)

        return panel
    }

    override fun doOKAction() {
        selectedOption = when {
            option1.isSelected -> 0
            option2.isSelected -> 1
            option3.isSelected -> 2
            else -> -1
        }
        super.doOKAction()
    }
}