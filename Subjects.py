from abc import ABC, abstractmethod
from country_list import countries_for_language
import country_converter as coco
from countryinfo import CountryInfo
import ast

class Question:

    def __init__(self, question_text, answer1, answer2, answer3, answer4, trueAnswer):
        self.question_text = question_text
        self.answer1 = answer1
        self.answer2 = answer2
        self.answer3 = answer3
        self.answer4 = answer4
        self.trueAnswer = trueAnswer


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

    def get_questions(self):
        main_question = 'What is the capital of '

        with open("Capitals.txt", "r", encoding="utf8") as txt_file:
            text = txt_file.read()
            print(text)
            answers = ast.literal_eval(text)
            return answers


    def init_questions(self, collection):
        pass

print(Capitals().get_questions())