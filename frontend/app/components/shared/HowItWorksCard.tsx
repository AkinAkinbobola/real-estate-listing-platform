interface HowItWorksCardProps {
    imageUrl: string
    cardTitle: string
    description: string
    buttonText: string
}

const HowItWorksCard = ({
                            cardTitle,
                            imageUrl,
                            description,
                            buttonText
                        }: HowItWorksCardProps) => {
    return (
        <div className={"rounded-[8.6px] bg-white  w-full md:w-[407.4px]"}>
            <img src={imageUrl} alt={"How it works images"} className={"object-cover w-full"}/>

            <div className={"flex flex-col text-center py-4 px-6 gap-2"}>
                <h1 className={"font-semibold text-2xl"}>{cardTitle}</h1>

                <p className={"font-light text-sm"}>
                    {description}
                </p>

                <div className={"flex items-center justify-center"}>
                    <button className={"w-fit py-2 px-8 border-2 border-black rounded-md"}>
                        {buttonText}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default HowItWorksCard;