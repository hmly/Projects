"""
MP3 Player

A simple program that plays your favorite music files.

Install pygame for Python 3
Ref: http://www.python-forum.org/viewtopic.php?f=25&t=2716

Pygame documentation
Ref: http://www.pygame.org/docs/ref/music.html#comment_pygame_mixer_music_get_busy
"""
from tkinter import *
from tkinter.ttk import Style
from mutagen.mp3 import MP3
import tkinter.filedialog as fdialog
import tkinter.messagebox as msgbox
import pygame
import getpass

BTN_WIDTH = 8
SECS = 60


class Player(Frame):
    def __init__(self, master):
        Frame.__init__(self, master)
        self.pack()

        self.paths = []
        self.paused = False
        self.instruct = Label(self, text='Press <Add> to start adding your favorite songs')
        self.stat = Label(self, text='')
        self.playlist = Listbox(self, width=BTN_WIDTH*7, height=15)
        self.addbtn = Button(self, text='Add', command=self.add, width=BTN_WIDTH)
        self.playbtn = Button(self, text='Play', command=self.play, width=BTN_WIDTH)
        self.repeatbtn = Button(self, text='Rewind', command=self.repeat, width=BTN_WIDTH)
        self.fowardbtn = Button(self, text='>>', command=self.next, width=BTN_WIDTH)
        self.prevbtn = Button(self, text='<<', command=self.previous, width=BTN_WIDTH)

        self.addbtn.grid(row=0, column=0)
        self.repeatbtn.grid(row=0, column=1)
        self.prevbtn.grid(row=0, column=2)
        self.playbtn.grid(row=0, column=3)
        self.fowardbtn.grid(row=0, column=4)
        self.instruct.grid(row=2, columnspan=5)
        self.playlist.grid(row=3, columnspan=5)
        self.stat.grid(row=5, columnspan=5)

        pygame.init()

    def add(self):
        files = fdialog.askopenfilenames(defaultextension='mp3',
                                         initialdir='/home/%s/Music/' % getpass.getuser())
        files = root.splitlist(files)

        for f in files:
            self.paths.append(f)
            dur = MP3(f).info.length
            MP3(f).info.pprint()
            dur = ' [%d:%02d]' % (dur // SECS, dur % SECS)
            f = f.split('/')[-1].split('.')[0] + dur  # Get song name
            self.playlist.insert(END, f)

    def play(self):
        if self.playlist.size() == 0:
            msgbox.showinfo('Notice', 'No songs added yet!')
        else:
            if pygame.mixer.music.get_busy() and self.paused:
                pygame.mixer.music.unpause()
                self.paused = False
                self.playbtn['text'] = 'Pause'
            elif pygame.mixer.music.get_busy():
                pygame.mixer.music.pause()
                self.paused = True
                self.playbtn['text'] = 'Play'
            else:
                pygame.mixer.music.stop()
                song_ind = self.playlist.curselection()[0]
                self.display_stat(self.paths[song_ind])
                pygame.mixer.music.load(self.paths[song_ind])
                pygame.mixer.music.play()

    def repeat(self):
        pygame.mixer.music.stop()
        pygame.mixer.music.play(-1)

    def next(self):
        song_ind = self.playlist.curselection()[0] + 1
        if self.playlist.size() > song_ind >= 0:
            pygame.mixer.music.stop()
            self.playlist.select_clear(0, END)
            self.playlist.selection_set(song_ind)
            self.display_stat(self.paths[song_ind])
            pygame.mixer.music.load(self.paths[song_ind])
            pygame.mixer.music.play()

    def previous(self):
        song_ind = self.playlist.curselection()[0] - 1
        if self.playlist.size() > song_ind >= 0:
            pygame.mixer.music.stop()
            self.playlist.select_clear(0, END)
            self.playlist.selection_set(song_ind)
            self.display_stat(self.paths[song_ind])
            pygame.mixer.music.load(self.paths[song_ind])
            pygame.mixer.music.play()

    def display_stat(self, song):
        stat = MP3(song).info.pprint().split(', ')
        self.stat['text'] = ' | '.join(stat[:-1])

if __name__ == '__main__':
    root = Tk()
    root.style = Style()
    root.style.theme_use('clam')
    root.title('MP3 Player')
    root.geometry('500x350')
    app = Player(root)
    app.mainloop()
