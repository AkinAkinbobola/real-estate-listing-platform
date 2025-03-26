import HowItWorksCard from "@/components/shared/HowItWorksCard";

const cardInfo = [
    {
        imageUrl: "/how-it-works-1.png",
        cardTitle: "Search",
        description: "Discover your perfect property using our intuitive search platform. Filter properties by location, price, and amenities with ease. Find your ideal home or investment property in just a few clicks.",
        buttonText: "Explore"
    },
    {
        imageUrl: "/how-it-works-2.png",
        cardTitle: "Inspect",
        description: "Explore properties through immersive virtual tours and high-quality photo galleries. Access detailed property reports and neighborhood insights. Schedule convenient viewings with expert local agents.",
        buttonText: "Learn More"
    },
    {
        imageUrl: "/how-it-works-3.png",
        cardTitle: "Secure",
        description: "Connect with verified real estate professionals who guide you through every transaction step. Receive personalized support for offer preparation, negotiations, and closing.",
        buttonText: "Get Started"
    },
]

const HowItWorks = () => {
    return (
        <div className={"flex flex-col gap-8"}>
            <h1 className={"font-semibold text-4xl"}>How It Works</h1>

            <div className={"flex flex-col md:flex-row gap-4 items-center justify-center"}>
                {
                    cardInfo.map(info => (
                        <HowItWorksCard
                            imageUrl={info.imageUrl}
                            cardTitle={info.cardTitle}
                            description={info.description}
                            buttonText={info.buttonText}
                        />
                    ))
                }

            </div>
        </div>
    );
};

export default HowItWorks;