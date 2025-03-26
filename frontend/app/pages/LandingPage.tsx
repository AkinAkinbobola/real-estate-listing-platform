import type {Route} from "../+types/root";
import Navbar from "@/components/shared/Navbar";
import Hero from "@/components/shared/Hero";
import HowItWorks from "@/components/shared/HowItWorks";
import JoinOurCommunity from "@/components/shared/JoinOurCommunity";

export function meta({}: Route.MetaArgs) {
    return [
        {title: "NextGen Realty"},
        {
            name: "description",
            content: "NextGen Realty makes home buying, selling, and renting simple with real-time listings, smart search tools, and expert guidance."
        },
    ];
}

const LandingPage = () => {
    return (
        <main className={"py-5 lg:py-10"}>
            <div className={"px-5 pb-[130px] space-y-4 md:space-y-8 2xl:space-y-52"}>
                <Navbar/>

                <Hero/>
            </div>

            <div className={"bg-dark-white px-5 py-10"}>
                <HowItWorks/>
            </div>

            <div className={"px-5 pt-10 pb-20"}>
                <JoinOurCommunity/>
            </div>
        </main>
    );
};

export default LandingPage;