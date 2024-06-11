export default function Screen({ children }: { children: React.ReactNode }) {
  return <div className='h-full w-[90%] flex-col justify-center'>{children}</div>
}
