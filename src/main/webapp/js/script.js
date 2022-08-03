let pageBody;

if (getCookie("darkMode") === "") {
    setCookie("darkMode", false)
}
if (getCookie("colorBlind") === "") {
    setCookie("colorBlind", false)
}
if (getCookie("gameStatus") === "") {
    setCookie("gameStatus", "IN_PROGRESS")
}
if (getCookie("boardState") === "") {
    setCookie("boardState", "[\"\", \"\", \"\", \"\", \"\", \"\"]")
}
if (getCookie("currentRowIndex") === "") {
    setCookie("currentRowIndex", 0)
}
if (getCookie("evaluations") === "") {
    setCookie("evaluations", "{one:[],two:[],three:[],four:[],five:[],six:[]}")
}

window.onload = function () {
    pageBody = document.body
    let isDarkModeEnabled = getCookie("darkMode")
    let isColorBlind = getCookie("colorBlind")

    let switchBtn;
    let switchKnob;
    let currentTileRow;
    let keyboardLetters = document.querySelectorAll(".letter")
    let tileBoard = document.querySelector(".board-row").parentElement

    if (isDarkModeEnabled === "true") {
        switchBtn = document.querySelector("#darkMode")
        switchKnob = document.querySelector("#darkModeSwitchKnob")
        pageBody.classList.add("dark")
        switchBtn.classList.add("switchBtn-checked")
        switchKnob.classList.add("switchKnob-checked")
    }

    if (isColorBlind === "true") {
        switchBtn = document.querySelector("#colorBlindMode")
        switchKnob = document.querySelector("#colorBlindSwitchKnob")
        pageBody.classList.add("color-blind")
        switchBtn.classList.add("switchBtn-checked")
        switchKnob.classList.add("switchKnob-checked")
    }

    // if (getCookie("lastPlayed") !== (new Date).toUTCString()) {
        // refreshTiles()
        // refreshKeyboard()
        // refreshCookies()
    // } else
        setBoard()

    if (getCookie("__u_id") === "") {
        $('#instructions').modal('show')
    }

    document.querySelector("#darkMode").addEventListener("click", function () {
        switchBtn = document.querySelector("#darkMode")
        switchKnob = document.querySelector("#darkModeSwitchKnob")
        if (switchBtn.classList.contains("switchBtn-checked")) {
            switchBtn.classList.remove("switchBtn-checked")
            switchKnob.classList.remove("switchKnob-checked")
            setCookie("darkMode", false)
            pageBody.classList.remove("dark")
        } else {
            switchBtn.classList.add("switchBtn-checked")
            switchKnob.classList.add("switchKnob-checked")
            setCookie("darkMode", true)
            pageBody.classList.add("dark")
        }
    });

    document.querySelector("#colorBlindMode").addEventListener("click", function () {
        switchBtn = document.querySelector("#colorBlindMode")
        switchKnob = document.querySelector("#colorBlindSwitchKnob")
        if (switchBtn.classList.contains("switchBtn-checked")) {
            switchBtn.classList.remove("switchBtn-checked")
            switchKnob.classList.remove("switchKnob-checked")
            setCookie("colorBlind", false)
            pageBody.classList.remove("color-blind")
        } else {
            switchBtn.classList.add("switchBtn-checked")
            switchKnob.classList.add("switchKnob-checked")
            setCookie("colorBlind", true)
            pageBody.classList.add("color-blind")
        }
    });

    keyboardLetters.forEach(letter => {
        letter.addEventListener('click', function() {
            currentTileRow = tileBoard.children.item(Number.parseInt(getCookie("currentRowIndex")))
            if (!(getCookie("gameStatus") === "IN_PROGRESS")) return;
            for (let i = 0; i < 5; i++) {
                let tile = currentTileRow.children.item(i).firstElementChild
                if (tile.getAttribute("data-state") === "empty") {
                    animateHeightAndWidth(tile)
                    tile.setAttribute("data-state", "tbd")
                    tile.insertAdjacentText("afterbegin", letter.getAttribute("data-key"))
                    break;
                }
            }
        });
    });

    document.querySelector("#btnEnter").addEventListener("click", function() {
        currentTileRow = tileBoard.children.item(Number.parseInt(getCookie("currentRowIndex")))
        if (!(getCookie("gameStatus") === "IN_PROGRESS")) return;
        let guess = ""
        for (let i = 0; i < 5; i++) {
            let tile = currentTileRow.children.item(i).firstElementChild
            if (tile.getAttribute("data-state") === "empty") {
                writeToToast("not_enough_letters","Not enough letters")
                return;
            }
            guess += tile.innerText.toLowerCase()
        }
        if (guess !== "") validateGuess(guess);
    });

    document.querySelector("#btnBackspace").addEventListener("click", function() {
        currentTileRow = tileBoard.children.item(Number.parseInt(getCookie("currentRowIndex")))
        if (!(getCookie("gameStatus") === "IN_PROGRESS")) return;

        for (let i = 4; i >= 0; i--) {
            let tile = currentTileRow.children.item(i).firstElementChild
            if (tile.getAttribute("data-state") !== "empty") {
                tile.innerText = ""
                tile.setAttribute("data-state", "empty")
                break;
            }
        }
    })

    $('#statistics').on('shown.bs.modal', function () {
        if (getCookie("gameStatus") !== "IN_PROGRESS") setTimer();
        let statisticsDiv;
        let guessDistribution;

        if (getCookie("stats") === "") {
            statisticsDiv = document.querySelector(".statistics")
            for (let i = 0; i < 4; i++) {
                let statisticsContainer = statisticsDiv.children.item(i)
                let statistic = statisticsContainer.firstElementChild
                statistic.innerText = ""
                statistic.insertAdjacentText("afterbegin", "0")
            }

            guessDistribution = document.querySelector(".guess-distribution")
            for (let i = 0; i < 6; i++) {
                let graphContainer = guessDistribution.children.item(i)
                let graphBar = graphContainer.lastElementChild
                let numGuesses = graphBar.firstElementChild

                graphBar.style.width = "8px"
                numGuesses.innerText = ""
                numGuesses.insertAdjacentText("beforeend", "0")
            }
        } else {
            let userStats = getStatistics()

            let Stats = userStats.split(",")
            Stats[0] = (Stats[0].split("{"))[1]
            Stats[Stats.length - 1] = ((Stats[Stats.length - 1]).split("}"))[0]

            for (let i = 0; i < Stats.length; i++) {
                Stats[i] = ((Stats[i].split(":"))[1]).trim()
            }

            let one = Number.parseInt(Stats[0])
            let two = Number.parseInt(Stats[1])
            let three = Number.parseInt(Stats[2])
            let four = Number.parseInt(Stats[3])
            let five = Number.parseInt(Stats[4])
            let six = Number.parseInt(Stats[5])
            let timesPlayed = Number.parseInt(Stats[6])
            let winPercentage = Number.parseInt(Stats[7])
            let currentStreak = Number.parseInt(Stats[8])
            let maxStreak = Number.parseInt(Stats[9])

            statisticsDiv = document.querySelector(".statistics")
            for (let i = 0; i < 4; i++) {
                let statisticsContainer = statisticsDiv.children.item(i)
                let statistic = statisticsContainer.firstElementChild
                statistic.innerText = ""
                if (i === 0) statistic.insertAdjacentText("beforeend", timesPlayed.toString())
                else if (i === 1) statistic.insertAdjacentText("beforeend", winPercentage.toString())
                else if (i === 2) statistic.insertAdjacentText("beforeend", currentStreak.toString())
                else if (i === 3) statistic.insertAdjacentText("beforeend", maxStreak.toString())
            }

            guessDistribution = document.querySelector(".guess-distribution")
            for (let i = 0; i < 6; i++) {
                let graphContainer = guessDistribution.children.item(i)
                let graphBar = graphContainer.lastElementChild
                let numGuesses = graphBar.firstElementChild
                let guess = i + 1;
                let guessNum;
                if (guess === 1) guessNum = one
                else if (guess === 2) guessNum = two
                else if (guess === 3) guessNum = three
                else if (guess === 4) guessNum = four
                else if (guess === 5) guessNum = five
                else guessNum = six

                let barWidth = Math.ceil(guessNum / timesPlayed) * 100

                if (guessNum === 0) graphBar.style.width = "8px"
                else {
                    graphBar.style.width = barWidth.toString() + "%"
                    graphBar.classList.add("graph-bar-filled")
                }
                numGuesses.innerText = ""
                numGuesses.insertAdjacentText("afterbegin", guessNum)

                if (getCookie("gameStatus") === "WON" && i === Number.parseInt(getCookie("currentRowIndex"))){
                    graphBar.classList.add("graph-bar-correct");
                }
            }
        }
    })
}

function validateGuess(guess) {
    $.ajax({
        type : "post",
        url : 'validate-guess-servlet',
        data : {
            guess: guess
        },
        success : function(responseText) {
            if (responseText === "[]") {
                writeToToast("not_in_word_list", "Not in word list");
                return;
            }
            flipTiles(responseText)
            updateKeyboard(guess, responseText)
        }
    })
}

function updateStatistics(event, index) {
    let id = getCookie("__u_id")
    if (id === "") id = "0"
    let isSuccessful = ""
    $.ajax({
        type: "post",
        url: 'statistics-servlet',
        data: {
            userID: id,
            event: event,
            rowIndex: index
        }
    })
    return "isSuccessful";
}

function getBoardState() {
    let boardState = getCookie("boardState")

    if (boardState === "[\"\", \"\", \"\", \"\", \"\", \"\"]") {
        return ["", "", "", "", "", ""];
    }

    let array = boardState.split(",")
    let first_word_array = array[0].split("[")
    let last_word_array = array[5].split("]")
    array[0] = first_word_array[1]
    array[5] = last_word_array[0]

    return array;
}

function setEvaluations(index, evaluation) {
    let evaluations = getEvaluations()
    if (index === 0) {
        evaluations["one"] = evaluation
        setCookie("evaluations", JSON.stringify(evaluations))
    } else if (index === 1) {
        evaluations["two"] = evaluation
        setCookie("evaluations", JSON.stringify(evaluations))
    } else if (index === 2) {
        evaluations["three"] = evaluation
        setCookie("evaluations", JSON.stringify(evaluations))
    } else if (index === 3) {
        evaluations["four"] = evaluation
        setCookie("evaluations", JSON.stringify(evaluations))
    } else if (index === 4) {
        evaluations["five"] = evaluation
        setCookie("evaluations", JSON.stringify(evaluations))
    } else if (index === 5) {
        evaluations["six"] = evaluation
        setCookie("evaluations", JSON.stringify(evaluations))
    }
}

function getEvaluations() {
    let evaluations = getCookie("evaluations")

    let evaluationsObject = {one:[], two:[], three:[], four:[], five:[], six:[]}

    let firstArray = evaluations.slice(evaluations.indexOf("["), evaluations.indexOf("]") + 1)
    evaluations = evaluations.replace(firstArray, "")

    let secondArray = evaluations.slice(evaluations.indexOf("["), evaluations.indexOf("]") + 1)
    evaluations = evaluations.replace(secondArray, "")

    let thirdArray = evaluations.slice(evaluations.indexOf("["), evaluations.indexOf("]") + 1)
    evaluations = evaluations.replace(thirdArray, "")

    let fourthArray = evaluations.slice(evaluations.indexOf("["), evaluations.indexOf("]") + 1)
    evaluations = evaluations.replace(fourthArray, "")

    let fifthArray = evaluations.slice(evaluations.indexOf("["), evaluations.indexOf("]") + 1)
    evaluations = evaluations.replace(fifthArray, "")

    let sixthArray = evaluations.slice(evaluations.indexOf("["), evaluations.indexOf("]") + 1)

    evaluationsObject["one"] = firstArray
    evaluationsObject["two"] = secondArray
    evaluationsObject["three"] = thirdArray
    evaluationsObject["four"] = fourthArray
    evaluationsObject["five"] = fifthArray
    evaluationsObject["six"] = sixthArray

    return evaluationsObject
}

function setBoardState(index, word) {
    let array = getBoardState()
    if (index === 0) {
        setCookie("boardState", "[" + word + ", \"\", \"\", \"\", \"\", \"\"]")
    } else if (index === 1) {
        setCookie("boardState", "[" + array[0].trim() + "," + word + ", \"\", \"\", \"\", \"\"]")
    } else if (index === 2) {
        setCookie("boardState", "[" + array[0].trim() + "," + array[1].trim() + "," + word + ", \"\", \"\", \"\"]")
    } else if (index === 3) {
        setCookie("boardState", "[" + array[0].trim() + "," + array[1].trim() + "," + array[2].trim() + "," + word + ", \"\", \"\"]")
    } else if (index === 4) {
        setCookie("boardState", "[" + array[0].trim() + "," + array[1].trim() + "," + array[2].trim() + "," + array[3].trim() + "," + word + ", \"\"]")
    } else if (index === 5) {
        setCookie("boardState", "[" + array[0].trim() + "," + array[1].trim() + "," + array[2].trim() + "," + array[3].trim() + "," + array[4].trim() + "," + word + "]")
    }
}

function getStatistics() {
    let statistics = getCookie("stats")
    return JSON.parse(statistics)
}

function setBoard() {
    let tileBoard = document.querySelector(".board-row").parentElement
    let array = getBoardState()
    let evaluations = getEvaluations()
    let evaluation;

    if (getCookie("boardState") === "[\"\", \"\", \"\", \"\", \"\", \"\"]") return;

    let currentRowIndex = 0;

    for (let i = 0; i < 6; i++) {
        if (array[i].trim() === "\"\"") break;

        if (i === 0) evaluation = evaluations["one"]
        else if (i === 1) evaluation = evaluations["two"]
        else if (i === 2) evaluation = evaluations["three"]
        else if (i === 3) evaluation = evaluations["four"]
        else if (i === 4) evaluation = evaluations["five"]
        else evaluation = evaluations["six"]

        if (evaluation === "[correct]") evaluation = ["correct", "correct", "correct", "correct", "correct"];
        else {
            let evaluationArray = evaluation.split(",")
            let first_word_array = evaluationArray[0].split("[")
            let last_word_array = evaluationArray[4].split("]")
            evaluationArray[0] = first_word_array[1]
            evaluationArray[4] = last_word_array[0]

            evaluation = evaluationArray
        }

        for (let j = 0; j < 5; j++) {
            let currentTileRow = tileBoard.children.item(currentRowIndex)
            let tile = currentTileRow.children.item(j).firstElementChild
            tile.insertAdjacentText("afterbegin", array[i].trim().charAt(j))
            tile.setAttribute("data-state", evaluation[j].trim())
        }
        currentRowIndex += 1
    }

    if (getCookie("gameStatus") !== "IN_PROGRESS") $('#statistics').modal('show');
}

function flipTiles(validated) {
    let tileBoard = document.querySelector(".board-row").parentElement
    let currentTileRow = tileBoard.children.item(Number.parseInt(getCookie("currentRowIndex")))

    if (validated === "[correct]") {
        for (let i = 0; i < 5; i++) {
            let tile = currentTileRow.children.item(i).firstElementChild
            tile.setAttribute("data-state", "correct")
        }
    } else {
        let array = validated.split(",")
        let first_word_array = array[0].split("[")
        let last_word_array = array[4].split("]")
        array[0] = first_word_array[1]
        array[4] = last_word_array[0]
        for (let i = 0; i < 5; i++) {
            let tile = currentTileRow.children.item(i).firstElementChild
            tile.setAttribute("data-state", array[i].trim())
        }
    }
}

function updateKeyboard(word, validated) {
    let keyboardLetters = document.querySelectorAll(".letter")

    if (validated === "[correct]") {
        for (let i = 0; i < 5; i++) {
            keyboardLetters.forEach(letter => {
                if (letter.getAttribute("data-key") === word.charAt(i)) letter.setAttribute("data-state", "correct");
            })
        }

        setCookie("gameStatus", "WON")

        let rowIndex = Number.parseInt(getCookie("currentRowIndex"))
        if (rowIndex === 0) writeToToast("win", "Genius")
        else if (rowIndex === 1) writeToToast("win", "Magnificent")
        else if (rowIndex === 2) writeToToast("win", "Impressive")
        else if (rowIndex === 3) writeToToast("win", "Splendid")
        else if (rowIndex === 4) writeToToast("win", "Great")
        else writeToToast("win", "Phew")
        setBoardState(Number.parseInt(getCookie("currentRowIndex")), word)
        setEvaluations(Number.parseInt(getCookie("currentRowIndex")), validated)
        if (updateStatistics( "win", rowIndex) === "success") $('#statistics').modal('show');
        setCookie("lastPlayed", (new Date()).toUTCString())

    } else {
        let array = validated.split(",")
        let first_word_array = array[0].split("[")
        let last_word_array = array[4].split("]")
        array[0] = first_word_array[1]
        array[4] = last_word_array[0]
        for (let i = 0; i < 5; i++) {
            keyboardLetters.forEach(letter => {
                if (letter.getAttribute("data-state") === "correct") return;
                if (letter.getAttribute("data-state") === "present" && array[i].trim() !== "correct") return;
                if (letter.getAttribute("data-key") === word.charAt(i)) letter.setAttribute("data-state", array[i].trim());
            })
        }

        if (Number.parseInt(getCookie("currentRowIndex")) === 5) {
            writeToToast("fail","The correct word is ")
            setCookie("gameStatus", "FAIL")
            updateStatistics("fail", Number.parseInt(getCookie("currentRowIndex")));
            $('#statistics').modal('show')
            setCookie("lastPlayed", (new Date()).toUTCString())
        }
        setBoardState(Number.parseInt(getCookie("currentRowIndex")), word)
        setEvaluations(Number.parseInt(getCookie("currentRowIndex")), validated)
        setCookie("currentRowIndex", (Number.parseInt(getCookie("currentRowIndex")) + 1).toString())
    }
}

let toastCounter = 0;
function writeToToast(event, message) {
    let toastContainer
    let toastDiv = document.createElement("div")

    if (event === "fail") {
        toastContainer = document.getElementById("fail-toast-container");

        // Give it a unique id
        toastDiv.id = "toast_"+ toastCounter;

        toastDiv.style.display = "block";
        toastDiv.classList.add("toast")

        toastDiv.innerText = message;
        toastContainer.appendChild(toastDiv);
    }
    else {
        toastContainer = document.getElementById("notification-toast-container")

        // Give it a unique id
        toastDiv.id = "toast_"+ toastCounter;

        toastDiv.style.display = "block";
        toastDiv.classList.add("toast")

        toastDiv.innerText = message;
        toastContainer.appendChild(toastDiv);
        setTimeout(function(){
            toastDiv.remove()
        },1500);
    }

    toastCounter++;
}

function refreshTiles() {
    let tileBoard = document.querySelector(".board-row").parentElement

    for (let i = 0; i < 6; i++) {
        let currentTileRow = tileBoard.children.item(i)
        for (let j = 0; j < 5; i++) {
            let tile = currentTileRow.children.item(j).firstElementChild
            tile.innerText = ""
            tile.setAttribute("data-state", "empty")
        }
    }
}

function refreshKeyboard() {
    let keyboardLetters = document.querySelectorAll(".letter")

    for (let i = 0; i < 5; i++) {
        keyboardLetters.forEach(letter => {
            letter.setAttribute("data-state", "")
        })
    }
}

function refreshCookies() {
    setCookie("gameStatus", "IN_PROGRESS")
    setCookie("boardState", "[\"\", \"\", \"\", \"\", \"\", \"\"]")
    setCookie("evaluations", "{one:[], two:[], three:[], four:[], five:[], six:[]}")
    setCookie("currentRowIndex", 0)
}

function setTimer() {
    let footer = document.querySelector(".statistics-footer")
    footer.style.display = "block";

    let div = document.getElementById("timer")
    setInterval(function(){
        let toDate=new Date();
        let tomorrow=new Date();

        tomorrow.setHours(24,0,0,0);
        let diffMS=tomorrow.getTime()/1000-toDate.getTime()/1000;
        let diffHr=Math.floor(diffMS/3600);

        diffMS=diffMS-diffHr*3600;
        let diffMi=Math.floor(diffMS/60);
        diffMS=diffMS-diffMi*60;

        let diffS=Math.floor(diffMS);
        let result=((diffHr<10)?"0"+diffHr:diffHr);
        result+=":"+((diffMi<10)?"0"+diffMi:diffMi);
        result+=":"+((diffS<10)?"0"+diffS:diffS);

        if (diffHr === 0 && diffMi === 0 && diffS === 0) {
            footer.style.display = "none";
            refreshTiles()
            refreshKeyboard()
            refreshCookies()
            return;
        }

        div.innerHTML=result;
    },1000);
}

function setCookie(cname, cvalue) {
    let expires = "expires="+ new Date(2147483647 * 1000).toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function animateHeightAndWidth(item) {
    $(item).animate({
        width: "50px",
        height: "50px",
    }, 250).animate({
        width: "40px",
        height: "40px",
    }, 250)
}
