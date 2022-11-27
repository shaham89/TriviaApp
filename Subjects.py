from abc import ABC, abstractmethod
import ast

class Question:

    def __init__(self, question_text, answer1, answer2, answer3, answer4, trueAnswer):
        self.question_text = question_text
        self.answer1 = answer1
        self.answer2 = answer2
        self.answer3 = answer3
        self.answer4 = answer4
        self.trueAnswer = trueAnswer

    def get_question(self):
        return self.question_text

    def get_options(self):
        return str(self.answer1 + ',' + self.answer2 + ',' + self.answer3 + ',' + self.answer4 + ',')

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
    def get_questions(self):
        main_question = 'What is the capital of '
        countries = []
        capitals_file = open("Data\\Capitals.txt", "r", encoding="utf8")
        answers = ast.literal_eval(capitals_file.read())
        capitals_file.close()

        countries_file = open("Data\\Countries.txt", "r", encoding="utf8")
        country_list = ast.literal_eval(countries_file.read())
        countries_file.close()

        questions = []
        for i in range(len(answers)):
            questions.append(Question(main_question + country_list[i] + "?", answers[i][0]
                                      , answers[i][1]
                                      , answers[i][2]
                                      , answers[i][3]
                                      , answers[i][1]))
        return questions


    def init_questions(self, collection):
        pass

qs = Capitals().get_questions()

for question in qs:
    print('Question:' + question.get_question() + '   answers: ' + question.get_options())
