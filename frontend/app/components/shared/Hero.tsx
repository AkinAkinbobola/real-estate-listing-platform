import {Search} from "lucide-react";

const Hero = () => {
    return (
        <div
            className={"w-full h-[363px] rounded-[40px] bg-linear-to-r from-dark-blue to-light-blue flex items-center relative"}>
            <div className={"flex flex-col mx-[80px] gap-y-8"}>
                <h1 className={"font-semibold text-5xl text-white"}>Find your Ideal Property</h1>

                <div className={"relative w-full"}>
                    <input placeholder={"Enter an address, city or zip code"}
                           className={"bg-white rounded-[10px] py-[15px] px-22.5px] outline-none w-full pl-3 pr-10"}/>
                    <Search className={"absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500"}/>
                </div>

                <img src={"/hero.png"} alt={"Hero Image"} className={"absolute right-16 bottom-0"}/>
            </div>
        </div>
    );
};

export default Hero;