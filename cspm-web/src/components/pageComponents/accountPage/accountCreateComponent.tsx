import AccountCreateButton from '@/components/button/accountCreateButton'
import AccountCancelButton from '@/components/button/accountCancelButton'
import AccountCreateInfoCheckButton from '@/components/button/accountInfoCheckButton'
import AccountNameInput from '@/components/input/accountNameInput'
import ClientInput from '@/components/input/clientInput'
import CodeInput from '@/components/input/codeInput'
import AccountIdInput from '@/components/input/AccountIdInput'
import RegionSelectBox from '@/components/selectBox/regionSelectBox'
import AccessSecretKeyInput from '@/components/input/accessSecretKeyInput'
import AccessKeyInput from '@/components/input/accessKeyInput'
import CommentInput from '@/components/input/commentInput'

export default function AccountCreateComponent() {
  return (
    <div>
      <div className='flex items-center gap-2'>
        <span className='mt-2 flex items-center text-xl text-red'>*</span>
        <span className='flex items-center text-xl'>필수 입력 항목</span>
      </div>
      <div className='mt-5 flex h-14 w-full items-center border-2 border-gray-300'>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='mt-2 flex items-center text-xl text-red'>*</span>
          <span className='flex items-center justify-center text-lg font-bold'>고객사</span>
        </div>
        <ClientInput />
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='mt-2 flex items-center text-xl text-red'>*</span>
          <span className='flex items-center justify-center text-lg font-bold'>CODE</span>
        </div>
        <CodeInput />
        <div className='flex w-[28%] flex-col'>
          <span className='line-clamp-1 text-nowrap text-sm text-red '>
            고객사를 구분하기 위한 CODE, RID를 구분하기 위한 구분자
          </span>
          <span className='line-clamp-1 text-nowrap text-sm text-red'>
            영문 5글자 ex{')'} 교보DTS {'=>'} dts, 교보 생명 {'=>'} life{' '}
          </span>
        </div>
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='mt-2 flex items-center text-xl text-red'>*</span>
          <span className='flex items-center justify-center text-lg font-bold'>Account Name</span>
        </div>
        <AccountNameInput />
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='mt-2 flex items-center text-xl text-red'>*</span>
          <span className='flex items-center justify-center text-lg font-bold'>Access Key</span>
        </div>
        <AccessKeyInput />
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='mt-2 flex items-center text-xl text-red'>*</span>
          <span className='flex items-center justify-center text-lg font-bold'>Secret Key</span>
        </div>

        <AccessSecretKeyInput />
        <AccountCreateInfoCheckButton />
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='mt-2 flex items-center text-xl text-red'>*</span>
          <span className='flex items-center justify-center text-lg font-bold'>Region</span>
        </div>
        <RegionSelectBox />
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='ml-4 flex items-center justify-center text-lg font-bold'>비고</span>
        </div>
        <CommentInput />
      </div>
      <div className='flex h-14 w-full items-center border-x-2 border-b-2 border-gray-300 '>
        <div className=' flex h-full w-[20%] gap-2 bg-gray-200 px-2'>
          <span className='ml-4 flex items-center justify-center text-lg font-bold'>
            Account ID
          </span>
        </div>
        <AccountIdInput />
      </div>

      <div className='flex h-14 w-full items-center justify-center gap-4'>
        <AccountCreateButton />
        <AccountCancelButton />
      </div>
    </div>
  )
}
