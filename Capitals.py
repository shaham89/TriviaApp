from abc import ABC, abstractmethod
import ast
import random
from Subjects import *

class Capitals(Subject):
    NAME = 'capitals'

    def get_name(self):
        return self.NAME

    @staticmethod
    def get_questions():
        main_question = 'What is the capital of '

        capitals_file = open("Data\\Capitals.txt", "r", encoding="utf8")
        answers = ast.literal_eval(capitals_file.read())
        capitals_file.close()

        countries_file = open("Data\\Countries.txt", "r", encoding="utf8")
        country_list = ast.literal_eval(countries_file.read())
        countries_file.close()

        questions = []
        for i in range(len(answers)):
            questions.append(Question(main_question + country_list[i] + "?", answers[i], answers[i][0]))

        return questions

    def init_questions(self, db):
        questions = self.get_questions()


        i = 0
        subject_name = self.get_name()
        print(subject_name)
        document_name = subject_name + "_subject"
        collection_path = Subject.QUESTION_COLLECTION_TEXT + "/" + subject_name + "_subject/" + subject_name + "_questions"
        print(collection_path)

        for q in questions:
            db.collection(collection_path).add({Question.QUESTION_TEXT: q.get_question(),
                                                Question.OPTIONS_TEXT: q.get_options(),
                                                Question.TRUE_ANSWER_TEXT: q.get_answer(),
                                                Question.INDEX_TEXT: i})
            i += 1
        db.collection(QUESTION_COLLECTION_TEXT).document(document_name).set({Question.LENGTH_TEXT: i})

