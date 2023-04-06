import java.text.ParseException
import java.text.SimpleDateFormat

import static java.util.Calendar.*

/**
 * Input e.g. 20:30
 * @param time
 * @return
 */
static def getDateTime(String time) {
    def tf = new SimpleDateFormat("HH:mm")
    def extractedTime

    try {
        extractedTime = tf.parse(time)
        def today = new Date()
        extractedTime[YEAR] = today[YEAR]
        extractedTime[DAY_OF_YEAR] = today[DAY_OF_YEAR]
    } catch (ParseException ex) {
        throw new Exception("Time must be of format HH:mm")
    }
    return extractedTime
}

static def sleepUntil(Date until, script) {
    script.echo "sleep unit $until"

    def TEN_MINUTES = 600 // seconds
    while (until.after(new Date())) {
        def wait = (until.getTime() - (new Date()).getTime()) / 1000 // secondds
        if (wait > TEN_MINUTES) {
            script.sleep(wait - TEN_MINUTES)
        } else {
            script.sleep(120) // 2 minutes
        }
    }
    script.echo "continue..."
}

return this