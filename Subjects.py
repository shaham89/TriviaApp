from abc import ABC, abstractmethod
import ast
import random


def md5_hash(str):
    import hashlib

    result = hashlib.md5(str.encode())

    return result.hexdigest()


class Question:

    QUESTION_TEXT = 'QuestionText'
    OPTIONS_TEXT = 'Options'
    TRUE_ANSWER_TEXT = 'TrueAnswer'
    INDEX_TEXT = 'Index'
    LENGTH_TEXT = 'Length'

    def __init__(self, question_text, options, trueAnswer):
        self.question_text = question_text
        self.options = list(options)
        # shuffle the list to make it more random
        random.shuffle(self.options)
        self.trueAnswer = md5_hash(trueAnswer)

    def get_question(self):
        return self.question_text

    def get_options(self):
        return self.options

    def get_answer(self):
        return self.trueAnswer


class Subject(ABC):


    QUESTION_COLLECTION_TEXT = u'subjects_questions'

    @abstractmethod
    def get_name(self):
        pass

    @abstractmethod
    def init_questions(self, db):
        pass

