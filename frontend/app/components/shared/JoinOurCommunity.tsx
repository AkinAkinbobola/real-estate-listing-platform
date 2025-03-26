import {Button} from "@/components/ui/button";

const JoinOurCommunity = () => {
    return (
        <div className={"flex gap-12 items-center flex-col-reverse md:flex-row justify-center"}>
            <img src={"/join-our-community.png"} alt={"Join our community"}/>

            <div className={"flex flex-col justify-center gap-3"}>
                <h1 className={"font-semibold text-3xl"}>Join our Community</h1>

                <p className={"font-light text-lg w-full md:w-[612px]"}>
                    Find properties and connect to top agents all on one platform. Search, schedule viewings, and make
                    connections— all through our streamlined platform.
                </p>

                <Button className={"w-fit"}>
                    Get Started
                </Button>
            </div>
        </div>
    );
};

export default JoinOurCommunity;