import os
import sys
from PyQt5.QtWidgets import QWidget, QApplication, QFileDialog, QMessageBox
from PyQt5 import QtCore
from PyQt5.uic import loadUi
from mq_communication import RabbitMq


def debug_trace(ui=None):
    from pdb import set_trace
    QtCore.pyqtRemoveInputHook()
    set_trace()
    # QtCore.pyqtRestoreInputHook()


class LibraryApp(QWidget):
    ROOT_DIR = os.path.dirname(os.path.abspath(__file__))

    def __init__(self):
        super(LibraryApp, self).__init__()
        ui_path = os.path.join(self.ROOT_DIR, 'exemplul_2.ui')
        loadUi(ui_path, self)
        self.search_btn.clicked.connect(self.search)
        self.save_as_file_btn.clicked.connect(self.save_as_file)
        self.add_book.clicked.connect(self.add_new_book)
        self.rabbit_mq = RabbitMq(self)

    def set_response(self, response):
        self.result.setText(response)

    def send_request(self, request):
        self.rabbit_mq.send_message(message=request)
        self.rabbit_mq.receive_message()

    def search(self):
        search_string = self.search_bar.text()
        request = None
        selection = None
        if not search_string:
            if self.json_rb.isChecked():
                request = 'print:json'
            elif self.html_rb.isChecked():
                request = 'print:html'
            elif self.xml_rb.isChecked():
                request = 'print:xml'
            else:
                request = 'print:raw'
        else:
            if self.json_rb.isChecked():
                selection = 'json'
            elif self.html_rb.isChecked():
                selection = 'html'
            elif self.xml_rb.isChecked():
                selection = 'xml'
            else:
                selection = 'raw'

            if self.author_rb.isChecked():
                request = 'find:{}:author={}'.format(selection,search_string)
            elif self.title_rb.isChecked():
                request = 'find:{}:title={}'.format(selection,search_string)
            else:
                request = 'find:{}:publisher={}'.format(selection,search_string)
        self.send_request(request)

    def save_as_file(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        file_path = str(
            QFileDialog.getSaveFileName(self,
                                        'Salvare fisier',
                                        options=options))
        if file_path:
            file_path = file_path.split("'")[1]
            if not file_path.endswith('.json') and not file_path.endswith(
                    '.html') and not file_path.endswith('.txt'):
                if self.json_rb.isChecked():
                    file_path += '.json'
                elif self.html_rb.isChecked():
                    file_path += '.html'
                elif self.xml_rb.isChecked():
                    file_path += '.xml'
                else:
                    file_path += '.txt'
            try:
                with open(file_path, 'w') as fp:
                    if file_path.endswith(".html"):
                        fp.write(self.result.toHtml())
                    else:
                        fp.write(self.result.toPlainText())
            except Exception as e:
                print(e)
                QMessageBox.warning(self, 'Exemplul 2',
                                    'Nu s-a putut salva fisierul')

    def add_new_book(self):
        title = self.add_title.text()
        author = self.add_author.text()
        publisher = self.add_publisher.text()
        content = self.add_content.text()
        if not title or not author or not publisher or not content:
            QMessageBox.warning(self,'Exemplul 2','Completati toate campurile mai intai')
        else:
            message = f"insert[{title}:{author}:{publisher}:{content}]"
            self.send_request(message)
            return

if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = LibraryApp()
    window.show()
    sys.exit(app.exec_())
