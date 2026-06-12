async function shortenUrl() {
    const longUrlInput = document.getElementById("longUrl");
    const message = document.getElementById("message");
    const resultBox = document.getElementById("resultBox");
    const shortUrlElement = document.getElementById("shortUrl");

    const longUrl = longUrlInput.value.trim();

    if (!longUrl) {
        showMessage("Please enter a valid URL.", "error");
        return;
    }

    try {
        showMessage("Creating short URL...", "info");

        const response = await fetch("/api/shorten", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                originalUrl: longUrl
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "Something went wrong");
        }

        const shortUrl = data.shortUrl || data.data?.shortUrl || data.url || data.shortCode;

        shortUrlElement.textContent = shortUrl;
        shortUrlElement.href = shortUrl;

        resultBox.classList.remove("hidden");
        showMessage("Short URL created successfully!", "success");

    } catch (error) {
        showMessage("Failed to create short URL. Check API request field name.", "error");
        console.error(error);
    }
}

function copyUrl() {
    const shortUrl = document.getElementById("shortUrl").textContent;

    navigator.clipboard.writeText(shortUrl).then(() => {
        showMessage("Short URL copied to clipboard!", "success");
    });
}

function showMessage(text, type) {
    const message = document.getElementById("message");
    message.textContent = text;

    if (type === "success") message.style.color = "#86efac";
    else if (type === "error") message.style.color = "#fecaca";
    else message.style.color = "#bfdbfe";
}