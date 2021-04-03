/**
 * A function to display/hide the navigation_display when
 * the hamburger-button is clicked.
 * */
function hamburger() {
    const navigation = document.getElementById("navigation_display");
    if (navigation.className === "navigationDisplay") {
        navigation.className += " navigationDisplayVisible";
    } else {
        navigation.className = "navigationDisplay";
    }
}
