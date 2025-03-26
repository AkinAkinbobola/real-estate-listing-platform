import {Button} from "@/components/ui/button";

const Subscription = () => {
    return (
        <div className={"bg-[url(/subscription.jpeg)] h-[430px] w-full bg-cover rounded-xl bg-black/50"}>
            <div className={"flex items-center justify-center h-full"}>
                <div className={"w-[616px] flex flex-col gap-12"}>
                    <h1 className={"text-white text-center font-semibold text-5xl"}>
                        Subscribe for News, Notifications and Deals
                    </h1>


                    <div className={"relative w-full"}>
                        <input placeholder={"your@email.com"}
                               className={"outline-none bg-white py-5 px-6 rounded-[10px] w-full"}/>

                        <Button className={"absolute right-3 top-1/2 transform -translate-y-1/2 bg-dark-blue"}>
                            Get Started
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Subscription;