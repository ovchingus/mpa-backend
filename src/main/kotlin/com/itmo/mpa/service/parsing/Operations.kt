package com.itmo.mpa.service.parsing

enum class Operations {

    NOT {
        override fun getValue(): String {
            return "not"
        }
    },

    AND {
        override fun getValue(): String {
            return "and"
        }
    },

    OR {
        override fun getValue(): String {
            return "or"
        }
    },

    GT {
        override fun getValue(): String {
            return "gt"
        }
    },

    LT {
        override fun getValue(): String {
            return "lt"
        }
    },

    EQ {
        override fun getValue(): String {
            return "eq"
        }
    };

    abstract fun getValue() : String
}