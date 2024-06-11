export default function Loading() {
  return (
    <div
      className='inline-block h-9 w-9 animate-spin rounded-full border-4 border-solid border-current border-e-transparent text-kyboNavy motion-reduce:animate-[spin_1.0s_linear_infinite]'
      role='status'
    >
      <span className='absolute m-px h-px w-px overflow-hidden whitespace-nowrap border-0'>
        Loading...
      </span>
    </div>
  )
}
