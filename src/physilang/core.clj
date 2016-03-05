(ns physilang.core
  (:require [instaparse.core :as insta]))

(def digit "digit = #'[0-9]+' ")
(def letter "letter = #'[a-zA-Z]+' ")
(def id "id = letter+ (letter | digit)* ")

(def pl-grammar-ebnf (str id digit letter))

(def pl-grammar
  (insta/parser pl-grammar-ebnf))