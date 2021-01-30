package com.crocsandcoffee.seattleplacesearch.main.model

/**
 * @author Omid
 *
 * Custom [Throwable] for handling bad/unsupported/corrupt data from the FourSquare API
 */
class WTFException : Throwable("WTF? Search venues response was null")