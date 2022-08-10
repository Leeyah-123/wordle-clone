<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0 shrink-to-fit=no"
    />
    <link rel="icon" href="image/icon.png">
    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
            integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
            crossorigin="anonymous"
    />
    <link rel="stylesheet" href="css/styles.css" />
<%--    <link--%>
<%--            rel="stylesheet"--%>
<%--            href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"--%>
<%--            integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"--%>
<%--            crossorigin="anonymous"--%>
<%--    />--%>
    <title>Wordle - The New York Times</title>
    <style>
        .close:not(:disabled):not(.disabled):hover {
            color: var(--color-tone-1);
            fill: var(--color-tone-1);
        }
        .modal-title {
            text-align: center;
        }
        .stats-modal {
            position: relative;
            border-radius: 8px;
            border: 1px solid var(--color-tone-6);
            background-color: var(--modal-content-bg);
            color: var(--color-tone-1);
            box-shadow: 0 4px 23px 0 rgb(0 0 0 / 20%);
            width: 90%;
            height: fit-content;
            max-height: fit-content;
            overflow-y: auto;
            max-width: var(--game-max-width);
            margin-left: 10px;
            padding: 16px;
            box-sizing: border-box;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous">
    </script>
    <script src="js/script.js"></script>
</head>
<body class="">
<!-- page header -->
<header id="top-nav">
    <!-- page title -->
    <div class="navbar-brand ml-md-5" id="nav-title">Wordle</div>
    <div class="ml-md-2" id="nav-btns">
        <!-- nav buttons -->
        <button
                class="btn nav-btn"
                data-toggle="modal"
                data-target="#instructions"
        >
            <!-- help icon -->
            <svg style="width: 28px; height: 28px" viewBox="0 0 24 24">
                <path
                        fill="var(--color-tone-1)"
                        d="M11,18H13V16H11V18M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20M12,6A4,4 0 0,0 8,10H10A2,2 0 0,1 12,8A2,2 0 0,1 14,10C14,12 11,11.75 11,15H13C13,12.75 16,12.5 16,10A4,4 0 0,0 12,6Z"
                />
            </svg>
        </button>

        <button
                class="btn nav-btn"
                data-toggle="modal"
                data-target="#statistics"
        >
            <!-- statistics icon -->
            <svg
                    xmlns="http://www.w3.org/2000/svg"
                    height="28"
                    viewBox="4 4 24 24"
                    width="28"
                    class="game-icon"
                    data-testid="icon-statistics"
            >
                <path
                        fill="var(--color-tone-1)"
                        d="M20.6666 14.8333V5.5H11.3333V12.5H4.33325V26.5H27.6666V14.8333H20.6666ZM13.6666 7.83333H18.3333V24.1667H13.6666V7.83333ZM6.66659 14.8333H11.3333V24.1667H6.66659V14.8333ZM25.3333 24.1667H20.6666V17.1667H25.3333V24.1667Z"
                ></path>
            </svg>
        </button>

        <button class="btn nav-btn" data-toggle="modal" data-target="#settings">
            <!-- settings icon -->
            <svg style="width: 28px; height: 28px" viewBox="0 0 24 24">
                <path
                        fill="var(--color-tone-1)"
                        d="M12,15.5A3.5,3.5 0 0,1 8.5,12A3.5,3.5 0 0,1 12,8.5A3.5,3.5 0 0,1 15.5,12A3.5,3.5 0 0,1 12,15.5M19.43,12.97C19.47,12.65 19.5,12.33 19.5,12C19.5,11.67 19.47,11.34 19.43,11L21.54,9.37C21.73,9.22 21.78,8.95 21.66,8.73L19.66,5.27C19.54,5.05 19.27,4.96 19.05,5.05L16.56,6.05C16.04,5.66 15.5,5.32 14.87,5.07L14.5,2.42C14.46,2.18 14.25,2 14,2H10C9.75,2 9.54,2.18 9.5,2.42L9.13,5.07C8.5,5.32 7.96,5.66 7.44,6.05L4.95,5.05C4.73,4.96 4.46,5.05 4.34,5.27L2.34,8.73C2.21,8.95 2.27,9.22 2.46,9.37L4.57,11C4.53,11.34 4.5,11.67 4.5,12C4.5,12.33 4.53,12.65 4.57,12.97L2.46,14.63C2.27,14.78 2.21,15.05 2.34,15.27L4.34,18.73C4.46,18.95 4.73,19.03 4.95,18.95L7.44,17.94C7.96,18.34 8.5,18.68 9.13,18.93L9.5,21.58C9.54,21.82 9.75,22 10,22H14C14.25,22 14.46,21.82 14.5,21.58L14.87,18.93C15.5,18.67 16.04,18.34 16.56,17.94L19.05,18.95C19.27,19.03 19.54,18.95 19.66,18.73L21.66,15.27C21.78,15.05 21.73,14.78 21.54,14.63L19.43,12.97Z"
                />
            </svg>
        </button>
    </div>
</header>

<!-- page body -->
<div class="wordle-game">
    <div class="board-container">
        <div class="board">
            <div class="board-row">
                <div class="" style="animation-delay: 0ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 100ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 200ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 300ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 400ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
            </div>
            <div class="board-row">
                <div class="" style="animation-delay: 0ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 100ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 200ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 300ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 400ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
            </div>
            <div class="board-row">
                <div class="" style="animation-delay: 0ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 100ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 200ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 300ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 400ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
            </div>
            <div class="board-row">
                <div class="" style="animation-delay: 0ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 100ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 200ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 300ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 400ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
            </div>
            <div class="board-row">
                <div class="" style="animation-delay: 0ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 100ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 200ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 300ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 400ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
            </div>
            <div class="board-row">
                <div class="" style="animation-delay: 0ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 100ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 200ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 300ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
                <div class="" style="animation-delay: 400ms">
                    <div
                            class="tile"
                            data-state="empty"
                            data-animation="idle"
                            data-testid="tile"
                    ></div>
                </div>
            </div>
        </div>
    </div>
    <div class="keyboard">
        <div class="keyboard-row">
            <button type="button" data-key="q" class="key letter">q</button>
            <button type="button" data-key="w" class="key letter">w</button>
            <button type="button" data-key="e" class="key letter">e</button>
            <button type="button" data-key="r" class="key letter">r</button>
            <button type="button" data-key="t" class="key letter">t</button>
            <button type="button" data-key="y" class="key letter">y</button>
            <button type="button" data-key="u" class="key letter">u</button>
            <button type="button" data-key="i" class="key letter">i</button>
            <button type="button" data-key="o" class="key letter">o</button>
            <button type="button" data-key="p" class="key letter">p</button>
        </div>
        <div class="keyboard-row">
            <div data-testid="spacer" class="half"></div>
            <button type="button" data-key="a" class="key letter">a</button>
            <button type="button" data-key="s" class="key letter">s</button>
            <button type="button" data-key="d" class="key letter">d</button>
            <button type="button" data-key="f" class="key letter">f</button>
            <button type="button" data-key="g" class="key letter">g</button>
            <button type="button" data-key="h" class="key letter">h</button>
            <button type="button" data-key="j" class="key letter">j</button>
            <button type="button" data-key="k" class="key letter">k</button>
            <button type="button" data-key="l" class="key letter">l</button>
            <div data-testid="spacer" class="half"></div>
        </div>
        <div class="keyboard-row">
            <button
                    type="button"
                    data-key="↵"
                    id="btnEnter"
                    class="key"
                    style="font-size: 13px"
            >
                enter</button
            ><button type="button" data-key="z" class="key letter">z</button
        ><button type="button" data-key="x" class="key letter">x</button
        ><button type="button" data-key="c" class="key letter">c</button
        ><button type="button" data-key="v" class="key letter">v</button
        ><button type="button" data-key="b" class="key letter">b</button
        ><button type="button" data-key="n" class="key letter">n</button
        ><button type="button" data-key="m" class="key letter">m</button
        ><button type="button" data-key="←" class="key" id="btnBackspace">
            <svg
                    xmlns="http://www.w3.org/2000/svg"
                    height="24"
                    viewBox="0 0 24 24"
                    width="24"
                    class="game-icon"
                    data-testid="icon-backspace"
            >
                <path
                        fill="var(--color-tone-1)"
                        d="M22 3H7c-.69 0-1.23.35-1.59.88L0 12l5.41 8.11c.36.53.9.89 1.59.89h15c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H7.07L2.4 12l4.66-7H22v14zm-11.59-2L14 13.41 17.59 17 19 15.59 15.41 12 19 8.41 17.59 7 14 10.59 10.41 7 9 8.41 12.59 12 9 15.59z"
                ></path>
            </svg>
        </button>
        </div>
    </div>

    <!-- toast containers -->
    <div class="toast-container" id="notification-toast-container"></div>
    <div class="toast-container" id="fail-toast-container"></div>
</div>

<!-- modals -->

<!-- instructions modal -->
<div
        class="modal fade"
        id="instructions"
        tabindex="-1"
        role="dialog"
        aria-labelledby="instructionsModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content settings-modal">
            <div class="modal-header">
                <h5 class="modal-title">how to play</h5>
                <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div>
                <section>
                    <div class="instructions">
                        <p>Guess the <strong>WORDLE</strong> in 6 tries.</p>
                        <p>Each guess must be a valid 5-letter word. Hit the enter button to submit.</p>
                        <p>After each guess, the color of the tiles will change to show how close your guess was to the word.</p>
                        <div class="examples">
                            <p><strong>Examples</strong></p>
                            <div class="example" aria-label="weary">
                                <div class="tile-container">
                                    <div class="tile" data-state="correct" data-animation="idle" id="letterW"></div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">e</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">a</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">r</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">y</div>
                                </div>
                                <p>The letter <strong>W</strong> is in the word and in the correct spot.</p>
                            </div>
                            <div class="example" aria-label="pills">
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">p</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="present" data-animation="idle" id="letterI"></div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">l</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">l</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">s</div>
                                </div>
                                <p>The letter <strong>I</strong> is in the word but in the wrong spot.</p>
                            </div>
                            <div class="example" aria-label="vague">
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">v</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">a</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">g</div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="absent" data-animation="idle" id="letterU"></div>
                                </div>
                                <div class="tile-container">
                                    <div class="tile" data-state="tbd" data-animation="idle">e</div>
                                </div>
                                <p>The letter <strong>U</strong> is not in the word in any spot.</p>
                            </div>
                        </div>
                        <p><strong>A new WORDLE will be available each day!</strong></p>
                        <p>Never miss a Wordle. <a href="#">Sign up</a> for our daily reminder email.</p>
                    </div>
                </section>
            </div>
        </div>
    </div>
</div>

<!-- statistics modal -->
<div
        class="modal fade"
        id="statistics"
        tabindex="-1"
        role="dialog"
        aria-labelledby="statisticsModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered statistics-modal" role="document">
        <div class="modal-content stats-modal">
            <div class="modal-header">
                <h5 class="modal-title">statistics</h5>
                <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="statistics">
                <div class="statistics-container">
                    <div class="statistic"></div>
                    <div class="statistic-label">Played</div>
                </div>
                <div class="statistics-container">
                    <div class="statistic"></div>
                    <div class="statistic-label">Win %</div>
                </div>
                <div class="statistics-container">
                    <div class="statistic"></div>
                    <div class="statistic-label">Current Streak</div>
                </div>
                <div class="statistics-container">
                    <div class="statistic"></div>
                    <div class="statistic-label">Max Streak</div>
                </div>
            </div>
            <h6 class="mt-3 mb-3" style="text-align: center;">Guess Distribution</h6>
            <div class="guess-distribution">
                <div class="graph-container">
                    <div class="guess">1</div>
                    <div class="graph">
                        <div class="num-guesses graph-bar"></div>
                    </div>
                </div>
                <div class="graph-container">
                    <div class="guess">2</div>
                    <div class="graph">
                        <div class="num-guesses graph-bar"></div>
                    </div>
                </div>
                <div class="graph-container">
                    <div class="guess">3</div>
                    <div class="graph">
                        <div class="num-guesses graph-bar"></div>
                    </div>
                </div>
                <div class="graph-container">
                    <div class="guess">4</div>
                    <div class="graph">
                        <div class="num-guesses graph-bar"></div>
                    </div>
                </div>
                <div class="graph-container">
                    <div class="guess">5</div>
                    <div class="graph">
                        <div class="num-guesses graph-bar"></div>
                    </div>
                </div>
                <div class="graph-container">
                    <div class="guess">6</div>
                    <div class="graph">
                        <div class="num-guesses graph-bar"></div>
                    </div>
                </div>
            </div>
            <div class="statistics-footer">
                <button type="button" class="next-button">
                    <span class="next-text">Next</span>
                </button>
<%--                <p>NEXT WORDLE</p>--%>
<%--                <div class="statistics-container">--%>
<%--                    <div class="statistic timer-container">--%>
<%--                        <div id="timer" data-testid="timer"></div>--%>
<%--                    </div>--%>
<%--                </div>--%>
            </div>
        </div>
    </div>
</div>

<!-- settings modal -->
<div
        class="modal fade"
        id="settings"
        tabindex="-1"
        role="dialog"
        aria-labelledby="settingsModalLabel"
        aria-hidden="true"
>
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content settings-modal">
            <div class="modal-header">
                <h5 class="modal-title">settings</h5>
                <button
                        type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Close"
                >
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div>
                <section>
                    <div class="setting">
                        <div class="setting-text">Dark Mode</div>
                        <div>
                            <button type="button" aria-checked="false" role="switch" id="darkMode" class="switchBtn">
                                <span class="switchKnob" id="darkModeSwitchKnob"></span>
                            </button>
                        </div>
                    </div>
                    <div class="setting">
                        <div class="setting-text">
                            High Contrast Mode
                            <div class="setting-description">For improved color vision</div>
                        </div>
                        <div>
                            <button type="button" aria-checked="false" role="switch" id="colorBlindMode" class="switchBtn">
                                <span class="switchKnob" id="colorBlindSwitchKnob"></span>
                            </button>
                        </div>
                    </div>
                </section>
                <section>
                    <div class="setting">
                        <div class="setting-text">Feedback</div>
                        <div>
                            <a href="#">Email</a>
                        </div>
                    </div>
                    <div class="setting">
                        <div class="setting-text">Community</div>
                        <div>
                            <a href="#">Twitter</a>
                        </div>
                    </div>
                    <div class="setting mb-5">
                        <div class="setting-text">Questions</div>
                        <div>
                            <a href="#">FAQ</a>
                        </div>
                    </div>
                </section>
            </div>
            <div class="modal-footnote">
                <div class="copyright">©&nbsp;2022&nbsp;The New York Times Company</div>
            </div>
        </div>
    </div>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"
></script>
</body>
</html>