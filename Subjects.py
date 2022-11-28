from abc import ABC, abstractmethod
import ast
import random

def md5_hash(str):
    import hashlib

    result = hashlib.md5(str.encode())

    return result.hexdigest()

class Question:

    def __init__(self, question_text, options, trueAnswer):
        self.question_text = question_text
        self.options = list(options)
        #shuffle the list to make it more random
        random.shuffle(self.options)
        self.trueAnswer = md5_hash(trueAnswer)

    def get_question(self):
        return self.question_text

    def get_options(self):
        return self.options

    def get_answer(self):
        return self.trueAnswer


class Subject(ABC):

    @abstractmethod
    def get_name(self):
        pass

    @abstractmethod
    def init_questions(self, db):
        pass



class Capitals(Subject):


    NAME = 'capitals'

    def get_name(self):
        return self.NAME

    # @staticmethod
    # def get_un_countries():
    #     cc = coco.CountryConverter()
    #     c_list = list(dict(countries_for_language('en')).values())
    #     print(len(c_list))
    #     #standard_names = cc.convert(names=c_list, to='name_short')
    #     UNmembershipDate = cc.convert(names=c_list, to='UNmember')
    #
    #     un_members = []
    #     for i in range(len(c_list)):
    #         # if the un membership date exist, the country is in the un
    #         if type(UNmembershipDate[i]) == int:
    #             un_members.append(c_list[i])
    #
    #     return un_members

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


    def init_questions(self, collection):
        questions = self.get_questions()
        QUESTION_TEXT = 'QuestionText'
        OPTIONS_TEXT = 'Options'
        TRUE_ANSWER_TEXT = 'TrueAnswer'
        for q in questions:
            collection.add({ QUESTION_TEXT: q.get_question(),
                             OPTIONS_TEXT: q.get_options(),
                             TRUE_ANSWER_TEXT: q.get_answer()})

# qs = Capitals().get_questions()
#
# for question in qs:
#     print('Question:' + question.get_question() + '   answers: ' + question.get_options())
