from abc import ABC, abstractmethod
from country_list import countries_for_language
import country_converter as coco
from countryinfo import CountryInfo

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


        questions = [Question(main_question + 'England', 'Glasgow', 'Birmingham', 'Cardiff', 'London', 'London'),
                     Question(main_question + 'Uzbekistan', 'Andijan', 'Samarkand', 'Tashkent', 'Namangan', 'Tashkent'),
                     Question(main_question + 'France', 'Marseille', 'Paris', 'Lyon', 'Nice', 'Paris'),
                     Question(main_question + 'Algeria', 'Algiers', 'Bechar', 'Oran', 'Touggourt', 'Algiers'),
                     Question(main_question + 'Trinidad and Tobago', 'Mon Repos', 'Laventille', 'Port of Spain', 'Chaguanas', 'Port of Spain'),
                     Question(main_question + 'Georgia', 'Kutaisi', 'Tbilisi', 'Sukhumi', 'Batumi', 'Tbilisi'),
                     Question(main_question + 'Nicaragua', 'Managua', 'Masava', 'Tipitapa', 'Leon', 'Managua'),
                     Question(main_question + 'Guatemala', 'Andijan', 'Samarkand', 'Tashkent', 'Namangan', 'Tashkent'),
                     Question(main_question + 'Uzbekistan', 'Andijan', 'Samarkand', 'Tashkent', 'Namangan', 'Tashkent'),
                     Question(main_question + 'Georgia', 'Kutaisi', 'Tbilisi', 'Sukhumi', 'Batumi', 'Tbilisi')

                     ]

    def init_questions(self, collection):
        pass
