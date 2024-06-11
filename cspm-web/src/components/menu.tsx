import Link from 'next/link'

export default function Menu() {
  return (
    <div className='relative flex h-16 w-full items-center'>
      <ul className=' flex h-12 w-full items-center justify-center gap-12'>
        <li className='flex h-full w-[150px] items-center rounded bg-gray-300'>
          <Link
            className='jusitfy-center flex h-full h-full w-full w-full items-center'
            href={'/resource'}
          >
            <span className='flex h-full w-full items-center justify-center'>리소스 스캔 결과</span>
          </Link>
        </li>
        <li className='flex h-full w-[150px] items-center rounded bg-gray-300'>
          <Link className='h-full w-full' href={'/scanresult'}>
            <span className='flex h-full w-full items-center justify-center'>
              {' '}
              취약점 스캔 결과
            </span>
          </Link>
        </li>
        <li className='flex h-full w-[150px] items-center rounded bg-gray-300'>
          <Link className='h-full w-full' href={'/config/account'}>
            <span className='flex h-full w-full items-center justify-center'>사용자 관리</span>
          </Link>
        </li>
      </ul>
    </div>
  )
}
